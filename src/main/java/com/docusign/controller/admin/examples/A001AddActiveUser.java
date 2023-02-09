package com.docusign.controller.admin.examples;

import com.docusign.DSConfiguration;
import com.docusign.admin.api.UsersApi;
import com.docusign.admin.model.*;
import com.docusign.common.WorkArguments;
import com.docusign.controller.admin.services.AddActiveUserService;
import com.docusign.core.model.DoneExample;
import com.docusign.core.model.Session;
import com.docusign.core.model.User;
import com.docusign.esign.api.AccountsApi;
import com.docusign.esign.api.GroupsApi;
import com.docusign.esign.client.ApiClient;
import com.docusign.esign.model.GroupInformation;
import com.docusign.esign.model.PermissionProfileInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Create new user
 * This example demonstrates how to create a new user with methods from Admin API.
 */
@Controller
@RequestMapping("/a001")
public class A001AddActiveUser extends AbstractAdminController {

    private static final String MODEL_LIST_PROFILES = "listProfiles";
    private static final String MODEL_LIST_GROUPS = "listGroups";
    private final User user;
    private final Session session;

    @Autowired
    public A001AddActiveUser(DSConfiguration config, Session session, User user) {
        super(config, "a001");
        this.user = user;
        this.session = session;
    }

    @Override
    protected void onInitModel(WorkArguments args, ModelMap model) throws Exception {
        super.onInitModel(args, model);
        ApiClient apiClient = new ApiClient("https://demo.docusign.net/restapi");
        apiClient.addDefaultHeader(HttpHeaders.AUTHORIZATION, BEARER_AUTHENTICATION + user.getAccessToken());
        
        // Step 3 start
        AccountsApi accountsApi = new AccountsApi(apiClient);
        UUID orgId = this.getOrganizationId(this.user.getAccessToken(), this.session.getBasePath());
        UUID accountId = this.getExistingAccountId(this.user.getAccessToken(), this.session.getBasePath(), orgId);
        PermissionProfileInformation permissionsInfo = accountsApi.listPermissions(String.valueOf(accountId));
        // Step 3 end

        model.addAttribute(MODEL_LIST_PROFILES, permissionsInfo.getPermissionProfiles());

        // Step 4 start
        GroupsApi groupsApi = new GroupsApi(apiClient);
        GroupInformation groupInformation = groupsApi.listGroups(String.valueOf(accountId));
        // Step 4 end

        model.addAttribute(MODEL_LIST_GROUPS, groupInformation.getGroups());
    }

    @Override
    protected Object doWork(WorkArguments args, ModelMap model, HttpServletResponse response) throws Exception {
        String accessToken = this.user.getAccessToken();
        String basePath = this.session.getBasePath();
        // Create a users api instance
        UsersApi usersApi = createUsersApi(accessToken, basePath);

        // Collect ids needed for the request
        UUID organizationId = this.getOrganizationId(accessToken, basePath);

        // Step 5 start
        UUID accountId = this.getExistingAccountId(accessToken, basePath, organizationId);
        NewUserResponse result = AddActiveUserService.createNewActiveUser(
                args.getGroupId(),
                args.getProfileId(),
                args.getEmail(),
                args.getUserName(),
                args.getFirstName(),
                args.getLastName(),
                usersApi,
                organizationId,
                accountId);

        // Process results
        DoneExample
                .createDefault(getTextForCodeExample().ExampleName)
                .withMessage(getTextForCodeExample().ResultsPageText)
                .withJsonObject(result)
                .addToModel(model, config);
        return DONE_EXAMPLE_PAGE;
    }
}

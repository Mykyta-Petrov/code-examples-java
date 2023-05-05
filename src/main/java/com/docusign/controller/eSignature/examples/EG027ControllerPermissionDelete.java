package com.docusign.controller.eSignature.examples;

import javax.servlet.http.HttpServletResponse;

import com.docusign.common.WorkArguments;
import com.docusign.core.model.DoneExample;
import com.docusign.core.model.Session;
import com.docusign.core.model.User;
import com.docusign.controller.eSignature.services.PermissionDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.docusign.DSConfiguration;
import com.docusign.esign.api.AccountsApi;
import com.docusign.esign.client.ApiException;
import com.docusign.esign.model.PermissionProfileInformation;


/**
 * Permission profiles are collections of account settings that determine the
 * behavior and actions available to the user groups to which they're applied.
 * This code example demonstrates how to delete a permission profile with the
 * eSignature REST API.
 */
@Controller
@RequestMapping("/eg027")
public class EG027ControllerPermissionDelete extends AbstractEsignatureController {

    private static final String MODEL_LIST_PROFILES = "listProfiles";

    @Autowired
    public EG027ControllerPermissionDelete(DSConfiguration config, Session session, User user) {
        super(config, "eg027", session, user);
    }

    @Override
    protected void onInitModel(WorkArguments args, ModelMap model) throws Exception {
        super.onInitModel(args, model);
        AccountsApi accountsApi = createAccountsApi(session.getBasePath(), user.getAccessToken());

        PermissionProfileInformation permissionsInfo = accountsApi.listPermissions(session.getAccountId());
        model.addAttribute(MODEL_LIST_PROFILES, permissionsInfo.getPermissionProfiles());
    }

    @Override
    protected Object doWork(WorkArguments args, ModelMap model, HttpServletResponse response) {
        // Step 2: Construct your API headers
        AccountsApi accountsApi = createAccountsApi(session.getBasePath(), user.getAccessToken());

        // Step 3: Call the eSignature Rest API to delete profile
        String curProfileId = args.getProfileId();
        try {
            PermissionDeleteService.permissionDelete(accountsApi, session.getAccountId(), curProfileId);

            // Step 4: Show 'done' (successful) page
            DoneExample.createDefault(getTextForCodeExampleByApiType().ExampleName)
                    .withMessage(getTextForCodeExampleByApiType().ResultsPageText.replaceFirst("\\{0}", curProfileId))
                    .addToModel(model, config);

            return DONE_EXAMPLE_PAGE;
        } catch (ApiException e) {
            new DoneExample()
                    .withTitle(exampleName)
                    .withName(title)
                    .withMessage(getTextForCodeExampleByApiType().CustomErrorTexts.get(0).ErrorMessage)
                    .addToModel(model, config);

            return ERROR_PAGE;
        }
    }
}

package com.docusign.controller.rooms.examples;

import com.docusign.DSConfiguration;
import com.docusign.common.WorkArguments;
import com.docusign.core.model.DoneExample;
import com.docusign.core.model.Session;
import com.docusign.core.model.User;
import com.docusign.rooms.api.FormGroupsApi;
import com.docusign.rooms.client.ApiException;
import com.docusign.rooms.model.FormGroupFormToAssign;
import com.docusign.rooms.model.FormGroupSummaryList;
import com.docusign.rooms.model.FormSummary;
import com.docusign.controller.rooms.services.AssignFormToFormGroupService;
import com.docusign.controller.rooms.services.GetFormSummaryListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Assigning a form to a form group.
 */
@Controller
@RequestMapping("/r009")
public class R009ControllerAssignFormToFormGroup extends AbstractRoomsController {

    private static final String MODEL_FORM_LIST = "formList";
    private static final String MODEL_FORM_GROUP_LIST = "formGroupList";
    private static final String FORM_ALREADY_EXISTS_ERROR_MESSAGE = "Form in the form group already exists";
    private final Session session;
    private final User user;
    private FormGroupsApi formGroupsApi;

    @Autowired
    public R009ControllerAssignFormToFormGroup(DSConfiguration config, Session session, User user) {
        super(config, "r009");
        this.session = session;
        this.user = user;
    }

    @Override
    protected void onInitModel(WorkArguments args, ModelMap model) throws Exception {
        super.onInitModel(args, model);

        // Step 3 Start
        this.formGroupsApi = createFormGroupsApi(
                this.session.getBasePath(), this.user.getAccessToken()
        );
        FormGroupSummaryList formGroupSummaryList = formGroupsApi.getFormGroups(this.session.getAccountId());
        // Step 3 End 

        // Step 4 Start
        List<FormSummary> forms = GetFormSummaryListService.getFormSummaryList(
                createFormLibrariesApi(session.getBasePath(), this.user.getAccessToken()),
                this.session.getAccountId());
        // Step 4 End

        model.addAttribute(MODEL_FORM_GROUP_LIST, formGroupSummaryList.getFormGroups());
        model.addAttribute(MODEL_FORM_LIST, forms);
    }

    @Override
    // ***DS.snippet.0.start
    protected Object doWork(WorkArguments args, ModelMap model,
                            HttpServletResponse response) throws IOException, ApiException {
        try {
            // Step 6 Start
            FormGroupFormToAssign formGroupFormToAssign = AssignFormToFormGroupService.assignFormToFormGroup(
                    this.formGroupsApi,
                    this.session.getAccountId(),
                    args.getFormId(),
                    args.getFormGroupId());
            // Step 6 End

            DoneExample.createDefault(this.title)
                    .withJsonObject(formGroupFormToAssign)
                    .withMessage(getTextForCodeExample().ResultsPageText
                            .replaceFirst("\\{0}", String.valueOf(args.getOfficeId()))
                            .replaceFirst("\\{1}", String.valueOf(args.getFormGroupId())))
                    .addToModel(model, config);
        } catch (ApiException apiException) {
            if (apiException.getMessage().contains(FORM_ALREADY_EXISTS_ERROR_MESSAGE)) {
                DoneExample.createDefault(this.title)
                        .withMessage(FORM_ALREADY_EXISTS_ERROR_MESSAGE)
                        .addToModel(model, config);
            } else if (apiException.getMessage().contains(getTextForCodeExample().CustomErrorTexts.get(0).ErrorMessageCheck)) {
                DoneExample.createDefault(this.title)
                        .withMessage(getTextForCodeExample().CustomErrorTexts.get(0).ErrorMessage)
                        .addToModel(model, config);
            }

            throw apiException;
        }
        return DONE_EXAMPLE_PAGE;
    }
}

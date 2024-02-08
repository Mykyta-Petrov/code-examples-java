package com.docusign.controller.webForms.examples;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.docusign.DSConfiguration;
import com.docusign.common.WorkArguments;
import com.docusign.controller.eSignature.services.CreateTemplateService;
import com.docusign.controller.webForms.services.CreateAndEmbedFormService;
import com.docusign.core.model.DoneExample;
import com.docusign.core.model.Session;
import com.docusign.core.model.User;
import com.docusign.esign.client.ApiClient;
import com.docusign.esign.client.ApiException;
import com.docusign.esign.model.EnvelopeTemplate;
import com.docusign.esign.model.EnvelopeTemplateResults;
import com.docusign.esign.model.TemplateSummary;
import com.docusign.webforms.model.WebFormInstance;
import com.docusign.webforms.model.WebFormSummary;
import com.docusign.webforms.model.WebFormSummaryList;

@Controller
@RequestMapping("/web001")
public class WEB001ControllerCreateAndEmbedForm extends AbstractWebFormsController {

    private static final String TEMPLATE_ID = "templateId";

    private static final String TEMPLATE_NAME = "Web Form Example Template";
    
    private static final String DOCUMENT_FILE_NAME = "World_Wide_Corp_Web_Form.pdf";
    
    private static final String WEB_FORM_CONFIG = "web-form-config.json";

    @Autowired
    public WEB001ControllerCreateAndEmbedForm(DSConfiguration config, Session session, User user) {
        super(config, "web001");
        this.session = session;
        this.user = user;
    }
    
    @Override
    protected void onInitModel(WorkArguments args, ModelMap model) throws Exception {
        super.onInitModel(args, model);
        model.addAttribute(TEMPLATE_ID, session.getTemplateId());
    }

    @Override
    protected Object doWork(WorkArguments args, ModelMap model,
                            HttpServletResponse response) throws ApiException, IOException, com.docusign.webforms.client.ApiException {
        if (session.getTemplateId() == null) {
            ApiClient eSignApiClient = createESignApiClient(session.getBasePath(), user.getAccessToken());
            String accountId = session.getAccountId();
            
            EnvelopeTemplateResults envelopeTemplateResults = CreateTemplateService.searchTemplatesByName(
                    eSignApiClient,
                    accountId,
                    TEMPLATE_NAME);

            if (Integer.parseInt(envelopeTemplateResults.getResultSetSize()) > 0) {
                EnvelopeTemplate template = envelopeTemplateResults.getEnvelopeTemplates().get(0);
                session.setTemplateId(template.getTemplateId());
            } else {
                session.setTemplateName(TEMPLATE_NAME);
                
                TemplateSummary template = CreateTemplateService.createTemplate(
                    eSignApiClient,
                    accountId,
                    CreateAndEmbedFormService.PrepareEnvelopeTemplate(TEMPLATE_NAME, DOCUMENT_FILE_NAME));

                session.setTemplateId(template.getTemplateId());
            }

            model.addAttribute(TEMPLATE_ID, session.getTemplateId());
            CreateAndEmbedFormService.addTemplateIdToForm(WEB_FORM_CONFIG, session.getTemplateId());

            return new RedirectView("web001");
        }

        com.docusign.webforms.client.ApiClient webFormsApiClient = createWebFormsApiClient(session.getBasePath(), user.getAccessToken());
        UUID accountIdUUID = UUID.fromString(session.getAccountId());

        WebFormSummaryList forms = CreateAndEmbedFormService.GetForms(webFormsApiClient, accountIdUUID);

        if (forms.getItems() == null || forms.getItems().size() == 0) {
            return new RedirectView("web001");
        }

        UUID formId = null;
        for (WebFormSummary form : forms.getItems()) {
            if (form.getFormProperties().getName() == TEMPLATE_NAME) {
                formId = UUID.fromString(form.getId());
            }
        }

        WebFormInstance form = CreateAndEmbedFormService.createInstance(webFormsApiClient, accountIdUUID, formId);
        
        return new RedirectView("web001");

        /*DoneExample.createDefault(this.title)
            .withJsonObject(form)
            //.withMessage()
            .addToModel(model, config);
        return DONE_EXAMPLE_PAGE;*/
    }
}

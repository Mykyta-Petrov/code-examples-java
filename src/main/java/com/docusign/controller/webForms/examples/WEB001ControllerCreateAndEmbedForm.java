package com.docusign.controller.webForms.examples;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

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

@Controller
@RequestMapping("/web001")
public class WEB001ControllerCreateAndEmbedForm extends AbstractWebFormsController {

    private static final String TEMPLATE_NAME = "Web Form Example Template";
    
    private static final String PDF_DOCUMENT_FILE_NAME = "World_Wide_Corp_Web_Form.pdf";
    
    private static final String WEB_FORM_CONFIG = "web-form-config.json";

    @Autowired
    public WEB001ControllerCreateAndEmbedForm(DSConfiguration config, Session session, User user) {
        super(config, "web001");
        this.session = session;
        this.user = user;
    }

    @Override
    protected Object doWork(WorkArguments args, ModelMap model,
                            HttpServletResponse response) throws ApiException, IOException {
        //EG008:start
        ApiClient apiClient = createApiClient(session.getBasePath(), user.getAccessToken());
        String accountId = session.getAccountId();
        EnvelopeTemplateResults envelopeTemplateResults = CreateTemplateService.searchTemplatesByName(
                apiClient,
                accountId,
                TEMPLATE_NAME);

        if (Integer.parseInt(envelopeTemplateResults.getResultSetSize()) > 0) {
            EnvelopeTemplate template = envelopeTemplateResults.getEnvelopeTemplates().get(0);
            session.setTemplateId(template.getTemplateId());
        } else {
            session.setTemplateName(TEMPLATE_NAME);
            
            TemplateSummary template = CreateTemplateService.createTemplate(
                apiClient,
                accountId,
                CreateAndEmbedFormService.PrepareEnvelopeTemplate(TEMPLATE_NAME, PDF_DOCUMENT_FILE_NAME));

            session.setTemplateId(template.getTemplateId());
        }
        //EG008:end

        CreateAndEmbedFormService.addTemplateIdToForm(WEB_FORM_CONFIG, session.getTemplateId());

        DoneExample.createDefault(this.title)
                .addToModel(model, config);
        return DONE_EXAMPLE_PAGE;
    }
}

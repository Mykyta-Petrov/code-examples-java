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
import com.docusign.core.model.Session;
import com.docusign.core.model.User;
import com.docusign.esign.model.EnvelopeTemplateResults;
import com.docusign.rooms.client.ApiException;
import com.docusign.webforms.client.ApiClient;

@Controller
@RequestMapping("/web001")
public class WEB001ControllerCreateAndEmbedForm extends AbstractWebFormsController {

    private static final String TEMPLATE_NAME = "Web Form Example Template";

    @Autowired
    public WEB001ControllerCreateAndEmbedForm(DSConfiguration config, Session session, User user) {
        super(config, "web001");
        this.session = session;
        this.user = user;
    }

    @Override
    protected Object doWork(WorkArguments args, ModelMap model,
                            HttpServletResponse response) throws IOException, ApiException {
        ApiClient apiClient = createApiClient(this.session.getBasePath(), this.user.getAccessToken());
        EnvelopeTemplateResults templates = CreateTemplateService.searchTemplatesByName(
            apiClient,
            session.getAccountId(),
            TEMPLATE_NAME);
    }
}

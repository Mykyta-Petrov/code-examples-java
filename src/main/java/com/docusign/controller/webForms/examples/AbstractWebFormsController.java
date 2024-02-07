package com.docusign.controller.webForms.examples;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import com.docusign.DSConfiguration;
import com.docusign.common.WorkArguments;
import com.docusign.core.controller.AbstractController;
import com.docusign.core.model.Session;
import com.docusign.core.model.User;
import com.docusign.esign.client.ApiClient;
import com.docusign.esign.client.ApiException;

@Controller
public class AbstractWebFormsController extends AbstractController {

    private static final String EXAMPLE_PAGES_PATH = "pages/webForms/examples/";

    protected Session session;

    protected User user;

    public AbstractWebFormsController(DSConfiguration config, String exampleName) {
        super(config, exampleName);
    }

    @Override
    protected Object doWork(WorkArguments args, ModelMap model,
                            HttpServletResponse response) throws ApiException, IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'doWork'");
    }

    protected ApiClient createApiClient(String basePath, String userAccessToken) {
        ApiClient apiClient = new ApiClient(basePath);
        apiClient.addDefaultHeader(HttpHeaders.AUTHORIZATION, BEARER_AUTHENTICATION + userAccessToken);
        return apiClient;
    }
    
    protected String getExamplePagesPath() {
        return AbstractWebFormsController.EXAMPLE_PAGES_PATH;
    }
}

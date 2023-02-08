package com.docusign.controller.monitor.examples;

import com.docusign.DSConfiguration;
import com.docusign.common.WorkArguments;
import com.docusign.core.model.DoneExample;
import com.docusign.core.model.Session;
import com.docusign.core.model.User;
import com.docusign.controller.monitor.services.GetMonitoringDataService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * Get monitoring data<br />
 * This example demonstrates how to get monitoring data from the Monitor API.
 */
@Controller
@RequestMapping("/m001")
public class M001GetMonitoringData extends AbstractMonitorController {

    private final Session session;
    private final User user;

    @Autowired
    public M001GetMonitoringData(DSConfiguration config, Session session, User user) {
        super(config, "m001");
        this.session = session;
        this.user = user;
    }

    @Override
    protected Object doWork(WorkArguments args, ModelMap model, HttpServletResponse response) throws Exception {
        String accessToken = this.user.getAccessToken();

        // Check, if you are using the JWT authentication
        // step 1 start
        accessToken = ensureUsageOfJWTToken(accessToken, this.session);
        // step 1 end

        JSONArray monitoringData = GetMonitoringDataService.getMonitoringData(createDataSetApi(accessToken, this.session));
        if (monitoringData.getJSONObject(0).has("Error"))
        {
            new DoneExample()
                    .withTitle(getTextForCodeExample().ExampleName)
                    .withName("")
                    .withMessage(monitoringData.getJSONObject(0).getString("Error"))
                    .addToModel(model, config);            
            return ERROR_PAGE;
        }

        // Cleaning the data from wrong symbols
        String monitoringDataCleaned = monitoringData.toString().replaceAll("'", "");

        // Process results
        DoneExample.createDefault(getTextForCodeExample().ExampleName)
                .withMessage(getTextForCodeExample().ResultsPageText)
                .withJsonObject(monitoringDataCleaned)
                .addToModel(model, config);

        return DONE_EXAMPLE_PAGE;
    }
}

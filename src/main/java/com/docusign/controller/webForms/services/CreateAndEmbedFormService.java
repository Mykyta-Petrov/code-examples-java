package com.docusign.controller.webForms.services;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.docusign.esign.api.TemplatesApi;
import com.docusign.esign.model.EnvelopeTemplate;
import com.docusign.esign.model.EnvelopeTemplateResults;
import com.docusign.webforms.api.FormInstanceManagementApi;
import com.docusign.webforms.api.FormManagementApi;
import com.docusign.webforms.client.ApiClient;
import com.docusign.webforms.client.ApiException;
import com.docusign.webforms.model.CreateInstanceRequestBody;
import com.docusign.webforms.model.WebFormInstance;
import com.docusign.webforms.model.WebFormSummaryList;
import com.docusign.webforms.model.WebFormValues;

public final class CreateAndEmbedFormService {
    public static WebFormSummaryList GetForms(
        ApiClient apiClient,
        UUID userAccessToken
    ) throws ApiException {
        FormManagementApi formManagementApi = new FormManagementApi(apiClient);
        return formManagementApi.listForms(userAccessToken);
    }

    public static void addTemplateIdToForm(String fileLocation, String templateId) {

    }

    public static WebFormInstance createInstance(
        ApiClient apiClient,
        UUID accountId,
        UUID formId
    ) throws ApiException {
        FormInstanceManagementApi formManagementApi = new FormInstanceManagementApi(apiClient);
        WebFormValues formValues = new WebFormValues();

        formValues.putAll(Map.of(
            "PhoneNumber", "555-555-5555",
            "Yes", new String[]{ "Yes" },
            "Company", "Tally",
            "JobTitle", "Programmer Writer"
        ));

        CreateInstanceRequestBody options = new CreateInstanceRequestBody()
            .clientUserId("1234-5678-abcd-ijkl")
            .formValues(formValues)
            .expirationOffset(3600);
            
        return formManagementApi.createInstance(accountId, formId, options);
    }

    public static EnvelopeTemplateResults searchTemplatesByName(
            ApiClient apiClient,
            String accountId,
            String templateName
    ) throws ApiException {
        TemplatesApi templatesApi = new TemplatesApi(apiClient);
        TemplatesApi.ListTemplatesOptions options = templatesApi.new ListTemplatesOptions();
        options.setSearchText(templateName);
        return templatesApi.listTemplates(accountId, options);
    }
}

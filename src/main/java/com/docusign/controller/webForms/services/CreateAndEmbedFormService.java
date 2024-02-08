package com.docusign.controller.webForms.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.core.io.ClassPathResource;

import com.docusign.controller.eSignature.examples.EnvelopeHelpers;
import com.docusign.esign.model.Checkbox;
import com.docusign.esign.model.DateSigned;
import com.docusign.esign.model.Document;
import com.docusign.esign.model.EnvelopeTemplate;
import com.docusign.esign.model.Recipients;
import com.docusign.esign.model.SignHere;
import com.docusign.esign.model.Signer;
import com.docusign.esign.model.Tabs;
import com.docusign.esign.model.Text;
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

    public static void addTemplateIdToForm(
        String fileName,
        String templateId
    ) throws FileNotFoundException, IOException {
        String targetString = "template-id";

        BufferedReader reader = new BufferedReader(new InputStreamReader(new ClassPathResource(fileName).getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String temp;
        while ((temp = reader.readLine()) != null) {
            stringBuilder.append(temp);
        }
        reader.close();

        String fileContent = stringBuilder.toString();
        String modifiedContent = fileContent.replace(targetString, templateId);

        //TODO
        /*BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        writer.write(modifiedContent);
        writer.close();*/
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

    public static EnvelopeTemplate PrepareEnvelopeTemplate(String templateName, String documentPdf) throws IOException {
        Document document = EnvelopeHelpers.createDocumentFromFile(documentPdf, "World_Wide_Web_Form", "1");
        
        Signer signer = new Signer()
            .roleName("signer")
            .recipientId("1")
            .routingOrder("1");

        signer.tabs(new Tabs()
            .checkboxTabs(List.of(
                new Checkbox()
                    .documentId("1")
                    .tabLabel("Yes")
                    .anchorString("/SMS/")
                    .anchorUnits("pixels")
                    .anchorXOffset("20")
                    .anchorYOffset("10")
            ))
            .signHereTabs(List.of(
                new SignHere()
                    .documentId("1")
                    .tabLabel("Signature")
                    .anchorString("/SignHere/")
                    .anchorUnits("pixels")
                    .anchorXOffset("20")
                    .anchorYOffset("10")
            ))
            .textTabs(List.of(
                new Text()
                    .documentId("1")
                    .tabLabel("FullName")
                    .anchorString("/FullName/")
                    .anchorUnits("pixels")
                    .anchorXOffset("20")
                    .anchorYOffset("10"),
                new Text()
                    .documentId("1")
                    .tabLabel("PhoneNumber")
                    .anchorString("/PhoneNumber/")
                    .anchorUnits("pixels")
                    .anchorXOffset("20")
                    .anchorYOffset("10"),
                new Text()
                    .documentId("1")
                    .tabLabel("Company")
                    .anchorString("/Company/")
                    .anchorUnits("pixels")
                    .anchorXOffset("20")
                    .anchorYOffset("10"),
                new Text()
                    .documentId("1")
                    .tabLabel("JobTitle")
                    .anchorString("/Title/")
                    .anchorUnits("pixels")
                    .anchorXOffset("20")
                    .anchorYOffset("10")
            ))
            .dateSignedTabs(List.of(
                new DateSigned()
                    .documentId("1")
                    .tabLabel("DateSigned")
                    .anchorString("/Date/")
                    .anchorUnits("pixels")
                    .anchorXOffset("20")
                    .anchorYOffset("10")
            ))
        );

        Recipients recepients = new Recipients()
            .signers(List.of(signer));
        
        return new EnvelopeTemplate()
            .description("Example template created via the API")
            .name(templateName)
            .shared("false")
            .documents(List.of(document))
            .emailSubject("Please sign this document")
            .recipients(recepients)
            .status("created");
    }
}

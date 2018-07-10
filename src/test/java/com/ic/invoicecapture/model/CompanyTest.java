package com.ic.invoicecapture.model;

import com.google.gson.JsonObject;
import com.ic.invoicecapture.json.JsonConverter;
import com.ic.invoicecapture.model.Company;
import com.ic.invoicecapture.model.builder.CompanyBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CompanyTest {

  @Test
  public void equals_empty() {

    Company company1 = new Company();
    Company company2 = new Company();

    Assertions.assertEquals(company1, company2);

    company1.setGid("12321");
    Assertions.assertNotEquals(company1, company2);
  }

  @Test
  public void hashCode_correctness() {
    Company company1 = new Company();
    Company company2 = new Company();

    Assertions.assertEquals(company1.hashCode(), company2.hashCode());

    company1.setName("some other name");
    Assertions.assertNotEquals(company1.hashCode(), company2.hashCode());
  }

  @Test
  public void toJson_jsonCorrectness() {
    CompanyBuilder companyBuilder = CompanyBuilder.buildTestCompanyBuilder();

    Company company = companyBuilder.buildCompany();
    String generatedJsonString = company.asJsonString();
    JsonObject generatedJson = JsonConverter.jsonStringAsJsonObject(generatedJsonString);
    JsonObject correctJson = companyBuilder.buildJsonObject();

    Assertions.assertEquals(generatedJson, correctJson);

    company.setCity("newCity");
    generatedJsonString = company.asJsonString();
    generatedJson = JsonConverter.jsonStringAsJsonObject(generatedJsonString);
    Assertions.assertNotEquals(generatedJson, correctJson);
  }
}
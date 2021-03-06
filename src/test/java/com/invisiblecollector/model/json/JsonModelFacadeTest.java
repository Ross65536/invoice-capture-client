package com.invisiblecollector.model.json;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Date;
import java.util.EnumMap;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.TreeMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;
import com.invisiblecollector.exceptions.IcException;
import com.invisiblecollector.model.Company;
import com.invisiblecollector.model.CompanyField;
import com.invisiblecollector.model.IModel;
import com.invisiblecollector.model.builder.CompanyBuilder;

public class JsonModelFacadeTest {

  private static String TEST_MAP_JSON = "{ \"a\":12, \"b\":\"string\", \"c\":null }";
  private static String CORRECT_MAP_JSON = "{ \"a\":\"12\", \"b\":\"string\", \"c\":null }";
  private static final String TEST_JSON_DATE = "\"2013-03-19\"";
  private static Map<String, String> CORRECT_MAP = new TreeMap<>();
  
  static {
    CORRECT_MAP.put("a", "12");
    CORRECT_MAP.put("b", "string");
    CORRECT_MAP.put("c", null);
  }

  private InputStream stringToInputStream(String string) {
    return new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8));
  }

  @Test
  public void parseStringStream_companyInstance() throws IcException {
    CompanyBuilder companyBuilder = CompanyBuilder.buildTestCompanyBuilder();
    Company correctCompany = companyBuilder.buildModel();

    String jsonString = companyBuilder.buildJsonObject().toString();

    InputStream inputStream = stringToInputStream(jsonString);

    JsonModelFacade jsonFacade = new JsonModelFacade();
    Company returnedCompany = jsonFacade.parseStringStream(inputStream, Company.class);

    Assertions.assertEquals(correctCompany, returnedCompany);
  }
  
  @Test
  public void parseStringStream_debtInstance() throws IcException {
    Date correctDate = new GregorianCalendar(2013, 2, 19).getTime();
    InputStream inputStream = stringToInputStream(TEST_JSON_DATE);
    Date returnedDate = new JsonModelFacade().parseStringStream(inputStream, Date.class);
    Assertions.assertEquals(correctDate, returnedDate);
  }
  

  @Test
  public void toJson_IModel_correctness() {
    CompanyBuilder companyBuilder = CompanyBuilder.buildTestCompanyBuilder();

    Company correctCompany = companyBuilder.buildModel();
    String returnedJson = new JsonModelFacade().toJson((IModel) correctCompany);

    JsonObject returnedJsonObj = JsonTestUtils.jsonStringAsJsonObject(returnedJson);
    JsonObject correctCompanyJsonObj = companyBuilder.buildJsonObject();
    Assertions.assertEquals(returnedJsonObj, correctCompanyJsonObj);
  }

  @Test
  public void parseStringStreamAsStringMap_correctness() throws IcException {


    InputStream inputStream = stringToInputStream(TEST_MAP_JSON);
    Map<String, String> map = new JsonModelFacade().parseStringStreamAsStringMap(inputStream);
    Assertions.assertEquals(CORRECT_MAP, map);
  }

  @Test
  public void toJson_stringMap_correctness() {
    String returnedJson = new JsonModelFacade().toJson(CORRECT_MAP);
    JsonTestUtils.assertJsonEquals(CORRECT_MAP_JSON, returnedJson);
  }

  @Test
  public void toJson_enumMap_correctness() {
    EnumMap<CompanyField, Object> companyMap = new EnumMap<>(CompanyField.class);
    companyMap.put(CompanyField.CITY, "new city");
    companyMap.put(CompanyField.ADDRESS, null);
    String correctJson = "{ 'city':'new city', 'address': null }".replaceAll("'", "\"");

    String json = new JsonModelFacade().toJson(companyMap);
    JsonTestUtils.assertJsonEquals(correctJson, json);
  }

  // tests indirectly for correct gson initialization
  @Test
  public void toJson_DateSuccess() throws ParseException {
    Date time = new GregorianCalendar(2013, 2, 19).getTime();
    String json = new JsonModelFacade().toJson(time);
    Assertions.assertEquals(TEST_JSON_DATE, json);
  }

  @Test
  public void toJson_DateNoExtraFields() {
    Date date = new GregorianCalendar(2013, 2, 19, 3, 1, 3).getTime();
    String json = new JsonModelFacade().toJson(date);
    Assertions.assertEquals(TEST_JSON_DATE, json);
  }

}

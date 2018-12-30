package com.invisiblecollector.model.builder;

import com.invisiblecollector.model.Company;

import java.util.HashMap;
import java.util.Map;

public class CompanyBuilder extends BuilderBase {

  private String address = null;
  private String city = null;
  private String country = null;
  private String gid = null;
  private String name = null;
  private Boolean notificationsEnabled = null;
  private String vatNumber = null;
  private String zipCode = null;

  public CompanyBuilder(String address, String city, String country, String gid, String name,
      Boolean notificationsEnabled, String vatNumber, String zipCode) {
    this.address = address;
    this.city = city;
    this.country = country;
    this.gid = gid;
    this.name = name;
    this.notificationsEnabled = notificationsEnabled;
    this.vatNumber = vatNumber;
    this.zipCode = zipCode;

  }
  
  @Override
  public Company buildModel() {
    Company comp = new Company();

    comp.setName(name);
    comp.setCity(city);
    comp.setAddress(address);
    comp.setId(gid);
    comp.setVatNumber(vatNumber);
    comp.setZipCode(zipCode);
    comp.setCountry(country);
    comp.setNotificationsEnabled(notificationsEnabled);
    return comp;
  }
  
  public static CompanyBuilder buildTestCompanyBuilder() {
    return new CompanyBuilder("testAdress", "testCity", "PT", "7657", "testName", false,
        "7657567", "123");
  }
  
  @Override
  public Map<String, Object> buildSendableJsonObject() {
    Map<String, Object> jsonObject = new HashMap<>();

    jsonObject.put("address", address);
    jsonObject.put("city", city);
    jsonObject.put("country", country);
    jsonObject.put("name", name);
    jsonObject.put("vatNumber", vatNumber);
    jsonObject.put("zipCode", zipCode);

    return jsonObject;
  }

  @Override
  public Map<String, Object> buildJsonObject() {
    Map<String, Object> jsonObject = buildSendableJsonObject();

    jsonObject.put("gid", gid);
    jsonObject.put("notificationsEnabled", notificationsEnabled);

    return jsonObject;
  }

  public CompanyBuilder setAddress(String address) {
    this.address = address;
    return this;
  }

  public CompanyBuilder setCity(String city) {
    this.city = city;
    return this;
  }

  public CompanyBuilder setCountry(String country) {
    this.country = country;
    return this;
  }

  public CompanyBuilder setGid(String gid) {
    this.gid = gid;
    return this;
  }

  public CompanyBuilder setName(String name) {
    this.name = name;
    return this;
  }

  public CompanyBuilder setNotificationsEnabled(Boolean notificationsEnabled) {
    this.notificationsEnabled = notificationsEnabled;
    return this;
  }

  public CompanyBuilder setVatNumber(String vatNumber) {
    this.vatNumber = vatNumber;
    return this;
  }

  public CompanyBuilder setZipCode(String zipCode) {
    this.zipCode = zipCode;
    return this;
  }



}

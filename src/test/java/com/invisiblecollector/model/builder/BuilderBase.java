package com.invisiblecollector.model.builder;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.invisiblecollector.model.IModel;
import com.invisiblecollector.model.json.GsonSingleton;

public abstract class BuilderBase {

  public static final String DATE_FORMAT = "yyyy-MM-dd";
  private static final Gson gsonInstance =  new GsonBuilder().serializeNulls().setDateFormat(DATE_FORMAT).create();

  public abstract IModel buildModel();

  protected <T> T buildModel(Class<T> classType) {
    String json = this.buildJson();

    Gson gson = GsonSingleton.getInstance();
    return gson.fromJson(json, classType);
  }

  public abstract Map<String, Object> buildSendableJsonObject();

  public Map<String, Object> buildSendableJsonObject(boolean stripNulls) {
    Map<String, Object> obj = buildSendableJsonObject();

    if (stripNulls) {
      obj.entrySet()
              .removeIf(entry -> entry.getValue() == null);
    }

    return obj;
  }

  public abstract Map<String, Object> buildJsonObject();

  public String buildJson() {
    return gsonInstance.toJson(this.buildModel());
  }

  /**
   * Strips all key-value with null values.
   *
   * @return
   */
  public String buildSendableJson() {
    return gsonInstance.toJson(this.buildSendableJsonObject(true));
  }
}

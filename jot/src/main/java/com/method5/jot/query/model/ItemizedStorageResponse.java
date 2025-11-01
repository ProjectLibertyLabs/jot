package com.method5.jot.query.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemizedStorageResponse {
  @JsonProperty("index")
  private int index;
  @JsonProperty("payload")
  private String payload;

  public ItemizedStorageResponse() {}

  public ItemizedStorageResponse(int index, String payload) {
    this.index = index;
    this.payload = payload;
  }

  public int getIndex() {
    return index;
  }

  public String getPayload() {
    return payload;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public void setPayload(String payload) {
    this.payload = payload;
  }

  @Override
  public String toString() {
    return "ItemizedStorageResponse {" +
        "index=" + index +
        ", payload=" + payload +
        '}';
  }
}

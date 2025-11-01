package com.method5.jot.query.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigInteger;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemizedStoragePageResponse {
  @JsonProperty("msa_id")
  private BigInteger msa_id;
  @JsonProperty("schema_id")
  private int schema_id;
  @JsonProperty("content_hash")
  private int content_hash;
  @JsonProperty("nonce")
  private int nonce;
  @JsonProperty("items")
  private List<ItemizedStorageResponse> items;

  public ItemizedStoragePageResponse() {}

  public ItemizedStoragePageResponse(BigInteger msaId, int schemaId, int contentHash, int nonce, List<ItemizedStorageResponse> items) {
    this.msa_id = msaId;
    this.schema_id = schemaId;
    this.content_hash = contentHash;
    this.nonce = nonce;
    this.items = items;
  }

  public BigInteger getMsa_id() {
    return msa_id;
  }

  public void setMsa_id(BigInteger msa_id) {
    this.msa_id = msa_id;
  }

  public Integer getSchema_id() {
    return schema_id;
  }

  public void setSchema_id(Integer schema_id) {
    this.schema_id = schema_id;
  }

  public Integer getContent_hash() {
    return content_hash;
  }

  public void setContent_hash(Integer content_hash) {
    this.content_hash = content_hash;
  }

  public Integer getNonce() {
    return nonce;
  }

  public void setNonce(Integer nonce) {
    this.nonce = nonce;
  }

  public List<ItemizedStorageResponse> getItems() {
    return items;
  }

  public void setItems(List<ItemizedStorageResponse> items) {
    this.items = items;
  }

  @Override
  public String toString() {
    return "ItemizedStoragePageResponse {" +
        "msaId=" + msa_id +
        ", schemaId='" + schema_id +
        ", contentHash=" + content_hash +
        ", nonce=" + nonce +
        ", items=" + items.toString() +
        "}";
  }

}

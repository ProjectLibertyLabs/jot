package com.method5.jot.extrinsic.model;

import java.math.BigInteger;
import java.util.List;

//TODO gotta figure out itemactions and the heterogenous vector
public class ItemizedSignaturePayloadV2 {
  private int schemaId;
  private BigInteger targetHash;
  private long expiration;
  private List<ItemAction> actions;

  public ItemizedSignaturePayloadV2(){
    //For Frameworks
  }

  public ItemizedSignaturePayloadV2(Integer schemaId, BigInteger targetHash, long expiration, List<ItemAction> actions) {
    this.schemaId = schemaId;
    this.targetHash = targetHash;
    this.expiration = expiration;
    this.actions = actions;
  }

  public Integer getSchemaId() {
    return schemaId;
  }

  public void setSchemaId(Integer schemaId) {
    this.schemaId = schemaId;
  }

  public BigInteger getTargetHash() {
    return targetHash;
  }

  public void setTargetHash(BigInteger targetHash) {
    this.targetHash = targetHash;
  }

  public long getExpiration() {
    return expiration;
  }

  public void setExpiration(long expiration) {
    this.expiration = expiration;
  }

  public List<ItemAction> getActions() {
    return actions;
  }

  public void setActions(List<ItemAction> actions) {
    this.actions = actions;
  }
}

package com.method5.jot.extrinsic.model;

public class AddItemAction implements ItemAction {
  private final byte[] payload;

  public AddItemAction(byte[] payload) {
    this.payload = payload;
  }

  @Override
  public byte getIndex() {
    return (byte) 0;
  }

  public byte[] getPayload() {
    return payload;
  }

  public static AddItemAction from(byte[] payload) {
    if (payload == null) throw new NullPointerException("payload is null");
    return new AddItemAction(payload);
  }
}

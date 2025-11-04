package com.method5.jot.extrinsic.model;

public class DeleteItemAction implements ItemAction {
  private final int itemIndex;

  DeleteItemAction(int itemIndex) {
    this.itemIndex = itemIndex;
  }

  @Override
  public byte getIndex() {
    return (byte) 1;
  }

  public int getItemIndex() {
    return this.itemIndex;
  }

  public static DeleteItemAction from(int itemIndex) {
    return new DeleteItemAction(itemIndex);
  }
}

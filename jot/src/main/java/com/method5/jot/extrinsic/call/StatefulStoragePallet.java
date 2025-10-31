package com.method5.jot.extrinsic.call;

import com.method5.jot.entity.MultiSignature;
import com.method5.jot.query.model.AccountId;
import com.method5.jot.rpc.Api;
import com.method5.jot.rpc.CallOrQuery;
import com.method5.jot.scale.ScaleWriter;

public class StatefulStoragePallet extends CallOrQuery {
  public StatefulStoragePallet(Api api) { super(api); }

  //TODO: Add the PalletStatefulStorageItemizedSignaturePayloadV2 object as a param

//  public Call applyItemActionsWithSignatureV2WithCapacity(AccountId delegatorKey, MultiSignature spRuntimeMultiSignature) {
//
//  }
//
//  public Call applyItemActionsWithSignatureV2(AccountId delegatorKey, MultiSignature spRuntimeMultiSignature) {
//
//  }
//
//  private byte[] createApplyItemActionsWithSignatureV2WithCapacityWriter(byte[] callIndex) {
//    ScaleWriter writer = new ScaleWriter();
//    writer.writeBytes(callIndex);
//    return writer.toByteArray();
//  };
//  private byte[] createApplyItemActionsWithSignatureV2Writer(byte[] callIndex) {
//    ScaleWriter writer = new ScaleWriter();
//    writer.writeBytes(callIndex);
//    return writer.toByteArray();
//  }
}

package com.method5.jot.extrinsic.call;

import com.method5.jot.entity.MultiSignature;
import com.method5.jot.extrinsic.model.ItemizedSignaturePayloadV2;
import com.method5.jot.query.model.AccountId;
import com.method5.jot.rpc.Api;
import com.method5.jot.rpc.CallOrQuery;
import com.method5.jot.scale.ScaleWriter;
import com.method5.jot.wallet.Wallet;

import java.nio.charset.StandardCharsets;

public class StatefulStoragePallet extends CallOrQuery {
  public StatefulStoragePallet(Api api) { super(api); }

  //TODO: Finish PalletStatefulStorageItemizedSignaturePayloadV2 object (itemizedPayload)

  public Call applyItemActionsWithSignatureV2WithCapacity(AccountId delegatorKey, MultiSignature spRuntimeMultiSignature, byte[] spCoreSignature, ItemizedSignaturePayloadV2 itemizedPayload) {
    Call applyItemActionsWithSignatureV2Call = applyItemActionsWithSignatureV2(delegatorKey, spRuntimeMultiSignature, spCoreSignature, itemizedPayload);
    return new Call(
      api,
      createApplyItemActionsWithSignatureV2WithCapacityWriter(
          getResolver().resolveCallIndex("FrequencyTxPayment", "pay_with_capacity"),
          applyItemActionsWithSignatureV2Call
      )
    );
  }

  public Call applyItemActionsWithSignatureV2(AccountId delegatorKey, MultiSignature spRuntimeMultiSignature, byte[] spCoreSignature, ItemizedSignaturePayloadV2 itemizedPayload) {
    return new Call(
      api, createApplyItemActionsWithSignatureV2Writer(
        getResolver().resolveCallIndex("StatefulStorage", "apply_item_actions_with_signature_v2"),
        delegatorKey,
        spRuntimeMultiSignature,
        spCoreSignature,
        itemizedPayload
      )
    );
  }

  private byte[] createApplyItemActionsWithSignatureV2WithCapacityWriter(byte[] callIndex, Call call) {
    ScaleWriter writer = new ScaleWriter();
    writer.writeBytes(callIndex);
    writer.writeBytes(call.callData());
    return writer.toByteArray();
  };
  private byte[] createApplyItemActionsWithSignatureV2Writer(byte[] callIndex, AccountId delegatorKey, MultiSignature spRuntimeMultiSignature, byte[] spCoreSignature, ItemizedSignaturePayloadV2 itemizedPayload) {
    ScaleWriter writer = new ScaleWriter();
    writer.writeBytes(callIndex);
    writer.writeBytes(delegatorKey.getPublicKey());
    writer.writeByte((byte) spRuntimeMultiSignature.getType().ordinal());
    writer.writeBytes(spCoreSignature);
    //TODO: Figure out itemizedPayload
    return writer.toByteArray();
  }
}

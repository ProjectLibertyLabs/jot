package com.method5.jot.extrinsic.call;

import com.method5.jot.entity.MultiSignature;
import com.method5.jot.extrinsic.model.AddItemAction;
import com.method5.jot.extrinsic.model.DeleteItemAction;
import com.method5.jot.extrinsic.model.ItemizedSignaturePayloadV2;
import com.method5.jot.query.model.AccountId;
import com.method5.jot.rpc.Api;
import com.method5.jot.rpc.CallOrQuery;
import com.method5.jot.scale.ScaleWriter;

public class StatefulStoragePallet extends CallOrQuery {
  public StatefulStoragePallet(Api api) { super(api); }

  //TODO: Finish PalletStatefulStorageItemizedSignaturePayloadV2 object (itemizedPayload)
  public Call applyItemActionsWithSignatureV2WithCapacity(AccountId delegatorKey, byte spRuntimeMultiSignatureType, byte[] spCoreSignature, ItemizedSignaturePayloadV2 itemizedPayload) {
    Call applyItemActionsWithSignatureV2Call = applyItemActionsWithSignatureV2(delegatorKey, spRuntimeMultiSignatureType, spCoreSignature, itemizedPayload);
    return new Call(
      api,
      createApplyItemActionsWithSignatureV2WithCapacityWriter(
          getResolver().resolveCallIndex("FrequencyTxPayment", "pay_with_capacity"),
          applyItemActionsWithSignatureV2Call
      )
    );
  }

  public Call applyItemActionsWithSignatureV2(AccountId delegatorKey, byte spRuntimeMultiSignatureType, byte[] spCoreSignature, ItemizedSignaturePayloadV2 itemizedPayload) {
    return new Call(
      api, createApplyItemActionsWithSignatureV2Writer(
        getResolver().resolveCallIndex("StatefulStorage", "apply_item_actions_with_signature_v2"),
        delegatorKey,
        spRuntimeMultiSignatureType,
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
  private byte[] createApplyItemActionsWithSignatureV2Writer(byte[] callIndex, AccountId delegatorKey, byte spRuntimeMultiSignatureType, byte[] spCoreSignature, ItemizedSignaturePayloadV2 itemizedPayload) {
    ScaleWriter writer = new ScaleWriter();
    writer.writeBytes(callIndex);
    writer.writeBytes(delegatorKey.getPublicKey());
    writer.writeByte((byte) spRuntimeMultiSignatureType);
    writer.writeBytes(spCoreSignature);
    //TODO: Figure out itemizedPayload
    if(itemizedPayload != null) {
      writer.writeInt(itemizedPayload.getSchemaId());
      writer.writeCompact(itemizedPayload.getTargetHash());
      writer.writeU32(itemizedPayload.getExpiration());
      itemizedPayload.getActions().forEach(action -> {
        if(action.getClass() == AddItemAction.class) {
          writer.writeByte(action.getIndex());
          writer.writeBytes(((AddItemAction) action).getPayload());
        } else {
          writer.writeByte(action.getIndex());
          writer.writeInt(((DeleteItemAction) action).getItemIndex());
        }
      });
    }
    return writer.toByteArray();
  }
}

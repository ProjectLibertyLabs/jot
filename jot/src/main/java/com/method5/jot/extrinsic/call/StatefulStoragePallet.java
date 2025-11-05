package com.method5.jot.extrinsic.call;

import com.method5.jot.entity.VariantType;
import com.method5.jot.extrinsic.model.AddItemAction;
import com.method5.jot.extrinsic.model.DeleteItemAction;
import com.method5.jot.extrinsic.model.ItemizedSignaturePayloadV2;
import com.method5.jot.query.model.AccountId;
import com.method5.jot.rpc.Api;
import com.method5.jot.rpc.CallOrQuery;
import com.method5.jot.scale.ScaleWriter;

import java.math.BigInteger;

public class StatefulStoragePallet extends CallOrQuery {
  public StatefulStoragePallet(Api api) { super(api); }

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
    writer.writeByte(spRuntimeMultiSignatureType);
    writer.writeBytes(spCoreSignature);
    if(itemizedPayload != null) {
      writer.writeCompact(BigInteger.valueOf(itemizedPayload.getSchemaId()));
      writer.writeCompact(itemizedPayload.getTargetHash());
      writer.writeU32(itemizedPayload.getExpiration());
      if(!itemizedPayload.getActions().isEmpty()) {
        itemizedPayload.getActions().forEach(action -> {
          writer.writeByte(VariantType.TUPLE.getType());
          if(action.getClass() == AddItemAction.class) {
            writer.writeByte(action.getIndex());
            //NOTE (Aziz 11/4/2025): This was a best guess, but this needs to have the payload length in bytes encoded
            writer.writeByte(((AddItemAction) action).getPayload().length * 4);
            writer.writeBytes(((AddItemAction) action).getPayload());
          } else {
            writer.writeByte(action.getIndex());
            writer.writeInt(((DeleteItemAction) action).getItemIndex());
          }
        });
      } else {
        writer.writeByte(0);
      }
    }
    return writer.toByteArray();
  }
}

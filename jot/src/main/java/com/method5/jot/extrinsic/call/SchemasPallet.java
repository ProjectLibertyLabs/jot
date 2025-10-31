package com.method5.jot.extrinsic.call;

import com.method5.jot.entity.VariantType;
import com.method5.jot.entity.metadata.PalletStorage;
import com.method5.jot.rpc.Api;
import com.method5.jot.rpc.CallOrQuery;
import com.method5.jot.scale.ScaleWriter;

import java.math.BigInteger;
import java.util.List;

public class SchemasPallet extends CallOrQuery {
  public SchemasPallet(Api api) {
    super(api);
  }

  public Call createSchemaV3(String model, Byte schemaModelType, Byte schemaPayloadLocation, List<Integer> schemaSettings, String schemaName) {
    return new Call(api, createSchemaV3Writer(
        getResolver().resolveCallIndex("Schemas", "create_schema_v3"),
        model,
        schemaModelType,
        schemaPayloadLocation,
        schemaSettings,
        schemaName
    ));
  }

  private byte[] createSchemaV3Writer(byte[] callIndex, String model, Byte schemaModelType, Byte schemaPayloadLocation, List<Integer> schemaSettings, String schemaName) {
    ScaleWriter writer = new ScaleWriter();
    writer.writeBytes(callIndex);
    writer.writeString(model);
    writer.writeByte(schemaModelType);
    writer.writeByte(schemaPayloadLocation);
    writer.writeByte(VariantType.TUPLE.getType());
    for (int setting: schemaSettings) {
      writer.writeI8((byte) setting);
    }
    if(schemaName != null) {
      writer.writeByte(VariantType.VARIANT.getType());
      writer.writeString(schemaName);
    } else {
      writer.writeByte(VariantType.COMPOSITE.getType());
    }

    return writer.toByteArray();
  }
}
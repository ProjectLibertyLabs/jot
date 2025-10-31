package com.method5.jot.query;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.method5.jot.rpc.Api;
import com.method5.jot.rpc.CallOrQuery;
import com.method5.jot.util.HexUtil;
import org.bouncycastle.util.encoders.Hex;

import java.math.BigInteger;

public class StatefulStorageRpc extends CallOrQuery {
  public StatefulStorageRpc(Api api) { super (api); }

  //TODO: Create a ItemizedStoragePageResponse class for this to return
  public String getItemizedStorage(BigInteger msaId, int schemaId) throws Exception {
    ArrayNode params = mapper.createArrayNode();
    params.add(msaId);
    params.add(schemaId);
    JsonNode result = api.send("statefulStorage_getItemizedStorage", params);
    System.out.println("Is there a result: " + result);
    return result.toPrettyString();
  }
}

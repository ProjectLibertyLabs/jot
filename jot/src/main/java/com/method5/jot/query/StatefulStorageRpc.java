package com.method5.jot.query;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.method5.jot.query.model.ItemizedStoragePageResponse;
import com.method5.jot.rpc.Api;
import com.method5.jot.rpc.CallOrQuery;

import java.math.BigInteger;

public class StatefulStorageRpc extends CallOrQuery {
  public StatefulStorageRpc(Api api) { super (api); }

  public ItemizedStoragePageResponse getItemizedStorage(BigInteger msaId, int schemaId) throws Exception {
    ArrayNode params = mapper.createArrayNode();
    params.add(msaId);
    params.add(schemaId);
    JsonNode result = api.send("statefulStorage_getItemizedStorage", params);
    System.out.println("Is there a result: " + result);
    return mapper.treeToValue(result, ItemizedStoragePageResponse.class);
  }
}

package com.method5.jot.query;

import com.method5.jot.rpc.Api;

/**
 * Query — Wrapper for all RPC queries in the Jot SDK.
 */
public class Query {
    protected Api api;
    protected AuthorRpc author;
    protected ChainRpc chain;
    protected PaymentRpc payment;
    protected StateRpc state;
    protected StatefulStorageRpc statefulStorage;
    protected StorageQuery storage;
    protected SystemRpc system;

    public Query(Api api) {
        this.api = api;

        this.author = new AuthorRpc(api);
        this.chain = new ChainRpc(api);
        this.payment = new PaymentRpc(api);
        this.state = new StateRpc(api);
        this.statefulStorage = new StatefulStorageRpc(api);
        this.storage = new StorageQuery(api);
        this.system = new SystemRpc(api);
    }

    public AuthorRpc author() {
        return author;
    }

    public ChainRpc chain() {
        return chain;
    }

    public PaymentRpc payment() {
        return payment;
    }

    public StateRpc state() {
        return state;
    }

    public StatefulStorageRpc statefulStorage() { return statefulStorage; }

    public StorageQuery storage() {
        return storage;
    }

    public SystemRpc system() {
        return system;
    }
}

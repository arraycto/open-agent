package com.opencloud.agent.client.handler;

public interface AsyncRPCCallback {

    void success(Object result);

    void fail(Exception e);

}

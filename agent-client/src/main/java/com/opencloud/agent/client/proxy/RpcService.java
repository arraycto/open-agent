package com.opencloud.agent.client.proxy;

import com.opencloud.agent.client.handler.RpcFuture;

public interface RpcService {
    RpcFuture call(String funcName, Object... args) throws Exception;
}
package com.opencloud.agent.client.proxy;

import com.opencloud.agent.protocol.RpcProtocol;

import java.lang.reflect.Proxy;

public class ProxyReflectFactory {
    /**
     * 负载均衡
     */
    @SuppressWarnings("unchecked")
    public static <T> T createService(Class<T> interfaceClass, String version) {
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new ObjectProxy<>(interfaceClass, version)
        );
    }

    /**
     * 负载均衡
     */
    public static <T> RpcService createAsyncService(Class<T> interfaceClass, String version) {
        return new ObjectProxy<T>(interfaceClass, version);
    }

    /**
     * 传入协议rpcProtocol
     * 将按照协议访问，不走负载均衡
     */
    @SuppressWarnings("unchecked")
    public static <T> T createService(RpcProtocol rpcProtocol, Class<T> interfaceClass, String version) {
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new ObjectProxy<>(rpcProtocol, interfaceClass, version)
        );
    }

    /**
     * 传入协议rpcProtocol
     * 将按照协议访问，不走负载均衡
     */
    public static <T> RpcService createAsyncService(RpcProtocol rpcProtocol, Class<T> interfaceClass, String version) {
        return new ObjectProxy<T>(rpcProtocol, interfaceClass, version);
    }
}

package com.opencloud.agent.client.proxy;

import com.opencloud.agent.client.AbstractClientHandler;
import com.opencloud.agent.client.connect.ConnectionManager;
import com.opencloud.agent.client.handler.RpcFuture;
import com.opencloud.agent.codec.RpcRequest;
import com.opencloud.agent.protocol.RpcProtocol;
import com.opencloud.agent.util.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

public class ObjectProxy<T> implements InvocationHandler, RpcService {
    private static final Logger logger = LoggerFactory.getLogger(ObjectProxy.class);
    private Class<T> clazz;
    private String version;
    private RpcProtocol rpcProtocol;

    /**
     * 服务负载获取
     */
    public ObjectProxy(Class<T> clazz, String version) {
        this.clazz = clazz;
        this.version = version;
    }

    /**
     * 定向执行
     */
    public ObjectProxy(RpcProtocol rpcProtocol, Class<T> clazz, String version) {
        this.clazz = clazz;
        this.version = version;
        this.rpcProtocol = rpcProtocol;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        final String equals = "equals";
        final String hashCode = "hashCode";
        final String toString = "toString";
        if (Object.class == method.getDeclaringClass()) {
            String name = method.getName();
            if (equals.equals(name)) {
                return proxy == args[0];
            } else if (hashCode.equals(name)) {
                return System.identityHashCode(proxy);
            } else if (toString.equals(name)) {
                return proxy.getClass().getName() + "@" +
                        Integer.toHexString(System.identityHashCode(proxy)) +
                        ", with InvocationHandler " + this;
            } else {
                throw new IllegalStateException(String.valueOf(method));
            }
        }

        RpcRequest request = new RpcRequest();
        request.setRequestId(UUID.randomUUID().toString());
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParameters(args);
        request.setVersion(version);
        // Debug
        if (logger.isDebugEnabled()) {
            logger.debug(method.getDeclaringClass().getName());
            logger.debug(method.getName());
            for (int i = 0; i < method.getParameterTypes().length; ++i) {
                logger.debug(method.getParameterTypes()[i].getName());
            }
            for (Object arg : args) {
                logger.debug(arg.toString());
            }
        }
        AbstractClientHandler handler = getClientHandler(true);
        RpcFuture rpcFuture = handler.sendRequest(request);
        return rpcFuture.get();
    }

    private AbstractClientHandler getClientHandler(boolean waitClientHandler) throws Exception {
        AbstractClientHandler handler;
        if (rpcProtocol == null) {
            // 服务负载获取
            String serviceKey = ServiceUtil.makeServiceKey(this.clazz.getName(), version);
            handler = ConnectionManager.getInstance().chooseHandler(waitClientHandler, serviceKey);
        } else {
            // 定向执行
            handler = ConnectionManager.getInstance().chooseHandler(waitClientHandler, rpcProtocol);
        }
        return handler;
    }


    @Override
    public RpcFuture call(String funcName, Object... args) throws Exception {
        AbstractClientHandler handler = getClientHandler(true);
        RpcRequest request = createRequest(this.clazz.getName(), funcName, args);
        return handler.sendRequest(request);
    }

    private RpcRequest createRequest(String className, String methodName, Object[] args, String requestId) {
        RpcRequest request = new RpcRequest();
        request.setRequestId(requestId);
        request.setClassName(className);
        request.setMethodName(methodName);
        request.setParameters(args);
        request.setVersion(version);

        Class<?>[] parameterTypes = new Class[args.length];
        // Get the right class type
        for (int i = 0; i < args.length; i++) {
            parameterTypes[i] = getClassType(args[i]);
        }
        request.setParameterTypes(parameterTypes);

        // Debug
        if (logger.isDebugEnabled()) {
            logger.debug(className);
            logger.debug(methodName);
            for (Class<?> parameterType : parameterTypes) {
                logger.debug(parameterType.getName());
            }
            for (Object arg : args) {
                logger.debug(arg.toString());
            }
        }

        return request;
    }

    private RpcRequest createRequest(String className, String methodName, Object[] args) {
        return createRequest(className, methodName, args, UUID.randomUUID().toString());
    }

    private Class<?> getClassType(Object obj) {
        Class<?> classType = obj.getClass();
        String typeName = classType.getName();
        switch (typeName) {
            case "java.lang.Integer":
                return Integer.TYPE;
            case "java.lang.Long":
                return Long.TYPE;
            case "java.lang.Float":
                return Float.TYPE;
            case "java.lang.Double":
                return Double.TYPE;
            case "java.lang.Character":
                return Character.TYPE;
            case "java.lang.Boolean":
                return Boolean.TYPE;
            case "java.lang.Short":
                return Short.TYPE;
            case "java.lang.Byte":
                return Byte.TYPE;
        }

        return classType;
    }

}

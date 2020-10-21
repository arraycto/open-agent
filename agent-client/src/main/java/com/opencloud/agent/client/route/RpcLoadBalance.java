package com.opencloud.agent.client.route;

import com.opencloud.agent.client.AbstractClientHandler;
import com.opencloud.agent.protocol.RpcProtocol;
import com.opencloud.agent.protocol.RpcServiceInfo;
import com.opencloud.agent.util.ServiceUtil;
import org.apache.commons.collections4.map.HashedMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 客户端长连接的管理
 * <p>
 * 客户端保持和服务进行长连接，不需要每次调用服务的时候进行连接，
 * 长连接的管理（通过Zookeeper获取有效的地址）。
 */
public abstract class RpcLoadBalance {
    /**
     * Service map: group by service name
     */
    protected Map<String, List<RpcProtocol>> getServiceMap(Map<RpcProtocol, AbstractClientHandler> connectedServerNodes) {
        Map<String, List<RpcProtocol>> serviceMap = new HashedMap<>();
        if (connectedServerNodes != null && connectedServerNodes.size() > 0) {
            for (RpcProtocol rpcProtocol : connectedServerNodes.keySet()) {
                for (RpcServiceInfo serviceInfo : rpcProtocol.getServiceInfoList()) {
                    String serviceKey = ServiceUtil.makeServiceKey(serviceInfo.getServiceName(), serviceInfo.getVersion());
                    List<RpcProtocol> rpcProtocolList = serviceMap.get(serviceKey);
                    if (rpcProtocolList == null) {
                        rpcProtocolList = new ArrayList<>();
                    }
                    rpcProtocolList.add(rpcProtocol);
                    serviceMap.putIfAbsent(serviceKey, rpcProtocolList);
                }
            }
        }
        return serviceMap;
    }

    /**
     * Route the connection for service key
     */
    public abstract RpcProtocol route(String serviceKey, Map<RpcProtocol, AbstractClientHandler> connectedServerNodes) throws Exception;
}

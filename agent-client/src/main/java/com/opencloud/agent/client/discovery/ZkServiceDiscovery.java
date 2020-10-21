package com.opencloud.agent.client.discovery;

import com.opencloud.agent.config.Constant;
import com.opencloud.agent.protocol.RpcProtocol;
import com.opencloud.agent.zookeeper.ZookeeperCuratorClient;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Client zk
 * 服务发现
 */
public class ZkServiceDiscovery {
    private static final Logger logger = LoggerFactory.getLogger(ZkServiceDiscovery.class);
    private ZookeeperCuratorClient zookeeperCuratorClient;
    private RpcNettyClient rpcNettyClient;
    private Integer retryTime = 1;

    public ZkServiceDiscovery(String registryAddress, Integer retryTime, String registerScheme, String registerAuth) {
        this.rpcNettyClient = new RpcNettyClient();
        this.retryTime = retryTime;
        this.zookeeperCuratorClient = new ZookeeperCuratorClient(registryAddress, registerScheme, registerAuth);
        discoveryService();
    }

    private void discoveryService() {
        try {
            // Get initial service info
            logger.info("Get initial service info");
            getServiceAndUpdateServer();
            // Add watch listener
            zookeeperCuratorClient.watchPathChildrenNode(Constant.ZK_REGISTRY_PATH, (curatorFramework, pathChildrenCacheEvent) -> {
                PathChildrenCacheEvent.Type type = pathChildrenCacheEvent.getType();
                switch (type) {
                    case CONNECTION_RECONNECTED:
                        logger.info("Reconnected to zk, try to get latest service list");
                        getServiceAndUpdateServer();
                        break;
                    case CHILD_ADDED:
                    case CHILD_UPDATED:
                    case CHILD_REMOVED:
                        logger.info("Service info changed, try to get latest service list");
                        getServiceAndUpdateServer();
                        break;
                    default:
                        logger.info("Default no watch listener");
                }
            });
        } catch (Exception ex) {
            logger.error("Watch node exception: " + ex.getMessage());
        }
    }

    private void getServiceAndUpdateServer() {
        try {
            List<String> nodeList = zookeeperCuratorClient.getChildren(Constant.ZK_REGISTRY_PATH);
            List<RpcProtocol> dataList = new ArrayList<>();
            for (String node : nodeList) {
                logger.debug("Service node: {}", node);
                byte[] bytes = zookeeperCuratorClient.getData(Constant.ZK_REGISTRY_PATH + "/" + node);
                String json = new String(bytes);
                RpcProtocol rpcProtocol = RpcProtocol.fromJson(json);
                dataList.add(rpcProtocol);
            }
            logger.info("Service node data: {}", dataList);
            //Update the service info based on the latest data
            updateConnectedServer(dataList);
        } catch (Exception e) {
            logger.error("Get node exception: " + e.getMessage());
        }
    }

    private void updateConnectedServer(List<RpcProtocol> dataList) {
        rpcNettyClient.updateConnectedServer(dataList, retryTime);
    }

    public void stop() {
        this.zookeeperCuratorClient.close();
        rpcNettyClient.stop();
    }

}

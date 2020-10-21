package com.opencloud.agent.zookeeper;

import cn.hutool.core.util.StrUtil;
import com.opencloud.agent.config.Constant;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;

import java.util.ArrayList;
import java.util.List;

public class ZookeeperCuratorClient {
    private CuratorFramework client;
    private List<ACL> acls = new ArrayList<>();

    public ZookeeperCuratorClient(String connectString, String registerScheme, String registerAuth, String namespace, int sessionTimeout, int connectionTimeout) {
        if (StrUtil.isBlank(registerScheme) || StrUtil.isBlank(registerAuth)) {
            client = CuratorFrameworkFactory.builder().namespace(namespace).connectString(connectString)
                    .sessionTimeoutMs(sessionTimeout).connectionTimeoutMs(connectionTimeout)
                    .retryPolicy(new ExponentialBackoffRetry(1000, 10))
                    .build();
        } else {
            client = CuratorFrameworkFactory.builder().namespace(namespace).connectString(connectString)
                    .sessionTimeoutMs(sessionTimeout).connectionTimeoutMs(connectionTimeout)
                    .retryPolicy(new ExponentialBackoffRetry(1000, 10))
                    .authorization(registerScheme, registerAuth.getBytes())
                    .build();
            Id user = new Id(registerScheme, AclUtils.getDigestUserPwd(registerAuth));
            acls.add(new ACL(ZooDefs.Perms.ALL, user));
        }
        client.start();
    }

    public ZookeeperCuratorClient(String connectString, int timeout, String registerScheme, String registerAuth) {
        this(connectString, registerScheme, registerAuth, Constant.ZK_NAMESPACE, timeout, timeout);
    }

    public ZookeeperCuratorClient(String connectString, String registerScheme, String registerAuth) {
        this(connectString, registerScheme, registerAuth, Constant.ZK_NAMESPACE, Constant.ZK_SESSION_TIMEOUT, Constant.ZK_CONNECTION_TIMEOUT);
    }

    public CuratorFramework getClient() {
        return client;
    }

    public void addConnectionStateListener(ConnectionStateListener connectionStateListener) {
        client.getConnectionStateListenable().addListener(connectionStateListener);
    }

    public void createPathData(String path, byte[] data) throws Exception {
        client.create().creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                .withACL(acls)
                .forPath(path, data);
    }

    public void updatePathData(String path, byte[] data) throws Exception {
        client.setData().forPath(path, data);
    }

    public void deletePath(String path) throws Exception {
        client.delete().forPath(path);
    }

    public void watchNode(String path, Watcher watcher) throws Exception {
        client.getData().usingWatcher(watcher).forPath(path);
    }

    public byte[] getData(String path) throws Exception {
        return client.getData().forPath(path);
    }

    public List<String> getChildren(String path) throws Exception {
        return client.getChildren().forPath(path);
    }

    public void watchTreeNode(String path, TreeCacheListener listener) {
        TreeCache treeCache = new TreeCache(client, path);
        treeCache.getListenable().addListener(listener);
    }

    public void watchPathChildrenNode(String path, PathChildrenCacheListener listener) throws Exception {
        PathChildrenCache pathChildrenCache = new PathChildrenCache(client, path, true);
        //BUILD_INITIAL_CACHE 代表使用同步的方式进行缓存初始化。
        pathChildrenCache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
        pathChildrenCache.getListenable().addListener(listener);
    }

    public void close() {
        client.close();
    }
}

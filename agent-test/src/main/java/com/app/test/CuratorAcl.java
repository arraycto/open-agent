package com.app.test;

import com.opencloud.agent.zookeeper.AclUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: zookeeper-connection
 * @description: curator操作zk节点acl权限演示demo
 * @author: 01
 * @create: 2018-04-29 19:53
 **/
public class CuratorAcl {

    /**
     * Curator客户端
     */
    public CuratorFramework client = null;
    /**
     * 集群模式则是多个ip
     */
    private static final String zkServerIps = "10.10.40.40:2181";

    public CuratorAcl() {
        RetryPolicy retryPolicy = new RetryNTimes(3, 5000);
        // 认证授权，登录用户
        client = CuratorFrameworkFactory.builder().authorization("digest", "user1:123456a".getBytes())
                .connectString(zkServerIps)
                .sessionTimeoutMs(10000).retryPolicy(retryPolicy)
                .authorization("digest", "opencloud:xxxxxx".getBytes())
                .namespace("workspace").build();
        client.start();
    }

    public void closeZKClient() {
        if (client != null) {
            this.client.close();
        }
    }

    public static void main(String[] args) throws Exception {

        // 实例化
        CuratorAcl cto = new CuratorAcl();
        boolean isZkCuratorStarted = cto.client.isStarted();
        System.out.println("当前客户的状态：" + (isZkCuratorStarted ? "连接中" : "已关闭"));

        String nodePath = "/test/1";

        // 自定义权限列表
        List<ACL> acls = new ArrayList<>();
        Id user1 = new Id("digest", AclUtils.getDigestUserPwd("opencloud:xxxxxx"));
        acls.add(new ACL(ZooDefs.Perms.ALL, user1));

        // 创建节点，使用自定义权限列表来设置节点的acl权限
        byte[] nodeData = "data".getBytes();
        cto.client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).withACL(acls).forPath(nodePath, nodeData);
        cto.closeZKClient();
        boolean isZkCuratorStarted2 = cto.client.isStarted();
        System.out.println("当前客户的状态：" + (isZkCuratorStarted2 ? "连接中" : "已关闭"));
    }
}
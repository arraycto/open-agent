package com.opencloud.agent.server.context;

import cn.hutool.core.date.DateUtil;
import com.opencloud.agent.codec.BasicInfo;
import com.opencloud.agent.codec.SocketInfo;
import com.opencloud.agent.server.RedisSource;
import com.opencloud.config.MessageType;
import io.netty.channel.socket.SocketChannel;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.opencloud.agent.BasicConfigurationConstants.AGENTN_NAME_REDIS_KEY;
import static com.opencloud.agent.BasicConfigurationConstants.PROXY_NAME_REDIS_KEY;

public class ApplicationServerContext {
    /**
     * Redis存储基础信息，提供Proxy使用
     * <p>
     * key = clientId，考虑到每个服务的唯一表示使用IP，不能使用channelId，因为每一次连接都会变，无法定位
     * value = SocketInfo
     */
    private static Map<String, BasicInfo> socketInfoMap = new ConcurrentHashMap<>();
    private static String serverHost;
    private static int serverPort;

    public static void init(String host, int port) {
        serverHost = host;
        serverPort = port;
    }

    /**
     * 链接建立后初始化Channel Id
     */
    public static void initSocketChannel(SocketChannel socketChannel) {
        // remote
        String remoteHost = socketChannel.remoteAddress().getHostString();
        int remotePort = socketChannel.localAddress().getPort();
        // channel
        String channelId = socketChannel.id().asLongText();
        // info
        SocketInfo socketInfo = new SocketInfo();
        socketInfo.setClientIp(remoteHost);
        socketInfo.setClientPort(remotePort);
        socketInfo.setServerIp(serverHost);
        socketInfo.setServerPort(serverPort);
        socketInfo.setChannelId(channelId);
        socketInfo.setCreateDate(new Date());

        BasicInfo basicInfo = new BasicInfo();
        basicInfo.setSocketChannel(socketChannel);
        basicInfo.setSocketInfo(socketInfo);
        socketInfoMap.put(channelId, basicInfo);
    }


    public static void removeSocketChannel(String channelId) {
        BasicInfo basicInfo = socketInfoMap.get(channelId);
        if (basicInfo == null || basicInfo.getSocketInfo() == null) {
            return;
        }
        RedisSource.del(basicInfo.getSocketInfo().getClientId());
        socketInfoMap.remove(channelId);
    }

    public static Map<String, BasicInfo> getChannels() {
        return socketInfoMap;
    }

    /**
     * 连接建立时设置信息
     */
    public static String setSocketInfoMessageType(int messageType) {
        String channelId = UserContextHolder.getInstance().getChannelId();
        if (channelId == null) {
            return null;
        }

        BasicInfo basicInfo = socketInfoMap.get(channelId);
        if (basicInfo == null || basicInfo.getSocketInfo() == null) {
            return channelId;
        }
        SocketInfo socketInfo = basicInfo.getSocketInfo();
        socketInfo.setMessageType(messageType);
        socketInfo.setExpireDate(DateUtil.offsetSecond(socketInfo.getCreateDate(), RedisSource.EXPIRE_DAY));
        // channel
        String clientId = null;
        if (MessageType.CLIENT.getType().equals(socketInfo.getMessageType())) {
            clientId = getAgentClientId(socketInfo.getClientIp());
        } else if (MessageType.PROXY.getType().equals(socketInfo.getMessageType())) {
            clientId = getProxyClientId(socketInfo.getClientIp());
        }
        if (clientId != null) {
            socketInfo.setClientId(clientId);
            RedisSource.setExpireDay(clientId, RedisSource.EXPIRE_DAY, socketInfo.toString());
        }
        return channelId;
    }


    private static String getAgentClientId(String clientIp) {
        return AGENTN_NAME_REDIS_KEY + clientIp;
    }

    private static String getProxyClientId(String clientIp) {
        return PROXY_NAME_REDIS_KEY + clientIp;
    }

    public static SocketChannel getSocketChannelByChannelId(String channelId) {
        if (channelId == null) {
            return null;
        }

        BasicInfo basicInfo = socketInfoMap.get(channelId);
        if (basicInfo == null) {
            return null;
        }
        return basicInfo.getSocketChannel();
    }

    public static void refresh() {
        String channelId = UserContextHolder.getInstance().getChannelId();
        if (channelId == null) {
            return;
        }

        BasicInfo basicInfo = socketInfoMap.get(channelId);
        if (basicInfo == null || basicInfo.getSocketInfo() == null) {
            return;
        }
        SocketInfo socketInfo = basicInfo.getSocketInfo();
        socketInfo.setCreateDate(new Date());
        socketInfo.setExpireDate(DateUtil.offsetSecond(socketInfo.getCreateDate(), RedisSource.EXPIRE_DAY));
        if (socketInfo.getClientId() == null) {
            return;
        }
        RedisSource.setExpireDay(socketInfo.getClientId(), RedisSource.EXPIRE_DAY, socketInfo.toString());

    }

}
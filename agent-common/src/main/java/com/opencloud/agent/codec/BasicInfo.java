package com.opencloud.agent.codec;

import io.netty.channel.socket.SocketChannel;
import lombok.Data;

/**
 * @author xielianjun
 */
@Data
public class BasicInfo {

    private SocketChannel socketChannel;

    private SocketInfo socketInfo;
}
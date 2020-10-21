package com.app.test.server;

import com.opencloud.agent.codec.BasicInfo;
import com.opencloud.agent.codec.RpcResponse;
import com.opencloud.agent.server.context.ApplicationServerContext;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.socket.SocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;

public class RpcServerSender {
    private final static Logger logger = LoggerFactory.getLogger(RpcServerSender.class);

    public static void sender() {
        Runnable sendTask = () -> {
            sendTaskLoop:
            for (; ; ) {
                logger.info("task is beginning...");
                try {
                    Map<String, BasicInfo> map = ApplicationServerContext.getChannels();
                    Iterator<String> it = map.keySet().iterator();
                    while (it.hasNext()) {
                        String key = it.next();
                        SocketChannel ctx = map.get(key).getSocketChannel();
                        RpcResponse response = new RpcResponse();
                        response.setRequestId("1");

                        response.setResult("ok");
                        ctx.writeAndFlush("response")
                                .addListener(
                                        (ChannelFutureListener) channelFuture -> {
                                            if (channelFuture.isSuccess()) {
                                                logger.info("Send response for request hahahah");
                                            } else {
                                                logger.error("Send response for request hahahah");
                                            }
                                        }
                                );
                    }
                } catch (Exception e) {
                    break sendTaskLoop;
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(sendTask).start();
    }
}

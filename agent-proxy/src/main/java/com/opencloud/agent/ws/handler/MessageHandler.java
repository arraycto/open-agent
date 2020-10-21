package com.opencloud.agent.ws.handler;

import com.opencloud.agent.executor.ExecutorResult;
import com.opencloud.config.MessageType;
import com.opencloud.agent.executor.ExecutorCenter;
import com.opencloud.agent.ws.IMessageHandler;
import com.opencloud.agent.ws.WebSocketInfo;
import com.opencloud.agent.ws.WebSocketServer;
import org.springframework.stereotype.Service;

import javax.websocket.Session;

@Service
public class MessageHandler implements IMessageHandler {

    @Override
    public MessageType getMessageType() {
        return MessageType.MESSAGE;
    }

    @Override
    public void execute(ExecutorResult executorResult, Session session) {
        WebSocketServer webSocketServer = ExecutorCenter.getWebsocket(session.getId());
        try {
            WebSocketInfo webSocketInfo = webSocketServer.getWebSocketInfo();
            if (webSocketInfo != null && webSocketInfo.getSession() != null) {
                // TODO
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

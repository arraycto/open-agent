package com.opencloud.agent.ws.handler;

import com.opencloud.config.MessageType;
import com.opencloud.agent.ws.IMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MessageHandlerFactory {

    private final static Map<Integer, IMessageHandler> MESSAGE_HANDLER = new ConcurrentHashMap<>();

    @Autowired
    private ApplicationContext applicationContext;

    public static IMessageHandler getMessageHandler(int messageType) {
        if (messageType <= 0) {
            return null;
        }
        return MESSAGE_HANDLER.get(messageType);
    }

    public static IMessageHandler getMessageHandler() {
        return MESSAGE_HANDLER.get(MessageType.MESSAGE.getType());
    }

    @PostConstruct
    public void init() {
        Map<String, IMessageHandler> messageHandlers = applicationContext.getBeansOfType(IMessageHandler.class);
        for (Map.Entry<String, IMessageHandler> messageHandler : messageHandlers.entrySet()) {
            IMessageHandler messageHandlerValue = messageHandler.getValue();
            MESSAGE_HANDLER.put(messageHandlerValue.getMessageType().getType(), messageHandlerValue);
        }
    }

}

package com.opencloud.agent.ws;

import com.opencloud.agent.executor.ExecutorResult;
import com.opencloud.config.MessageType;

import javax.websocket.Session;

public interface IMessageHandler {

    MessageType getMessageType();

    void execute(ExecutorResult executorResult, Session session);

}

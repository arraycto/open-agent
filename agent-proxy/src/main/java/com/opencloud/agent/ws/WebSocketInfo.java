package com.opencloud.agent.ws;

import javax.websocket.Session;

public class WebSocketInfo {

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    /**
     * 接收客户端ip
     */
    private String clientIp = "";
    /**
     * 区分是从浏览器过来，还是client过来
     */
    private int messageType;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }
}

package com.opencloud.agent;

public enum ExecutorStatus {
    STATUS_NO(-1, "无状态"),
    STATUS_SEND_CHANNEL(0, "请求已发送Channel服务器"),
    STATUS_WAITING(1, "等待执行"),
    STATUS_DOING(2, "正在执行"),
    STATUS_FINISH(3, "执行完成"),

    STATUS_SEND_PROXY_ERROR(4, "Channel不存在,请求发送Channel服务器失败"),
    STATUS_NO_CLIENT(5, "无可用客户端实例"),
    STATUS_NO_CHANNEL(6, "无可用Channel实例"),

    STATUS_SEND_CHANNEL_ERROR(7, "发送channel失败"),
    STATUS_SEND_CHANNEL_WAIT(8, "等待转发channel服务器执行"),
    STATUS_SEND_CHANNEL_WAIT_ERROR(9, "等待转发channel服务器执行,失败"),
    STATUS_SEND_CHANNEL_WAIT_SUCCESS(10, "转发channel服务器执行,成功"),
    STATUS_SEND_CHANNEL_QUEUE_ERROR(11, "转发channel服务器执行,失败"),

    STATUS_RESPONSE_PROXY_ERROR(12, "请求返回Proxy服务器失败"),
    ;

    private int status;

    private String message;

    ExecutorStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

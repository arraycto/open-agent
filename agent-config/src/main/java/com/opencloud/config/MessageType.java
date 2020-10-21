package com.opencloud.config;

public enum MessageType {
    /**
     * 标题
     */
    TITLE(1),
    /**
     * 内容
     */
    MESSAGE(2),
    /**
     * 关闭
     */
    CLOSE(3),
    /**
     * 代理服务信息
     */
    PROXY(4),
    /**
     * 客户端信息
     */
    CLIENT(5),
    /**
     * 会话信息
     */
    SESSIONID(6),
    /**
     * 命令
     */
    COMMAND(7),
    /**
     * 浏览器查看执行的LOG信息
     */
    LOOK(8),
    /**
     * 浏览器查看未执行的LOG信息
     */
    UNLOOK(9),
    /**
     * 打开查看日志
     */
    OPEN(10),
    ;

    private Integer type;

    MessageType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
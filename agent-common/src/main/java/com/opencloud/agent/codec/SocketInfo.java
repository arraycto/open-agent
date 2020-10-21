package com.opencloud.agent.codec;

import com.opencloud.agent.util.IpUtils;
import com.opencloud.agent.util.JsonUtil;
import lombok.Data;

import java.util.Date;


/**
 * @author xielianjun
 */
@Data
public class SocketInfo {
    /**
     * 客户端Id
     */
    private String clientId;
    /**
     * 接收客户端ip
     */
    private String clientIp = "";
    /**
     * 客户端Port
     */
    private Integer clientPort;
    /**
     * 区分是从浏览器过来，还是client过来
     */
    private Integer messageType;
    /**
     * channelId
     */
    private String channelId;

    /**
     * serverIp
     */
    private String serverIp = IpUtils.getLocalIp();
    /**
     * serverPort
     */
    private Integer serverPort;

    /**
     * 创建
     */
    private Date createDate;
    /**
     * 到期
     */
    private Date expireDate;

    @Override
    public String toString() {
        return JsonUtil.objectToJson(this);
    }
}
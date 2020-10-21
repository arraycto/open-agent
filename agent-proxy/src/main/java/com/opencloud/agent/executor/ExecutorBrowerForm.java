package com.opencloud.agent.executor;

import lombok.Data;

import java.util.List;

@Data
public class ExecutorBrowerForm {

    private String command;
    /**
     * 客户端IP标示
     */
    private List<String> clientIds;

    private String sessionId;

    private Long executeId;
}

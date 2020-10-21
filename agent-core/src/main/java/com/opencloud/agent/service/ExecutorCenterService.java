package com.opencloud.agent.service;

import com.opencloud.agent.executor.ExecutorFromProxy;
import com.opencloud.agent.executor.ExecutorResult;

public interface ExecutorCenterService {
    String RESPONSE = "response";
    String REQUEST = "request";

    ExecutorResult request(ExecutorFromProxy executorFromProxy);

    void response(ExecutorResult executorResult);

}

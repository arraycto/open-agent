package com.opencloud.agent.client.executor;

import com.opencloud.agent.executor.ExecutorResult;
import com.opencloud.agent.executor.ExecutorCenter;

public class ExecutorResponseHandler extends Thread {

    private ExecutorResult executorResult;

    public ExecutorResponseHandler(ExecutorResult executorResult) {
        this.executorResult = executorResult;
    }

    @Override
    public void run() {
        ExecutorCenter.doResponseResult(executorResult);
    }


}

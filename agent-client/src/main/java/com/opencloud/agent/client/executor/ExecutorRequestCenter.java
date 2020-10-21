package com.opencloud.agent.client.executor;

import com.opencloud.agent.executor.ExecutorFromServer;

import java.util.concurrent.ThreadPoolExecutor;

public class ExecutorRequestCenter {

    private final ThreadPoolExecutor threadPoolExecutor;
    private final Integer retryTime;

    public ExecutorRequestCenter(ThreadPoolExecutor threadPoolExecutor, Integer retryTime) {
        this.threadPoolExecutor = threadPoolExecutor;
        this.retryTime = retryTime;
    }

    public void executorCenter(Object msg) {
        if (msg instanceof ExecutorFromServer) {
            ExecutorFromServer fromServerInfo = (ExecutorFromServer) msg;
            ExecutorRequestHandler eh = new ExecutorRequestHandler();
            eh.setFromServerInfo(fromServerInfo);
            eh.setRetryTime(retryTime);
            threadPoolExecutor.execute(eh);
        }
    }
}

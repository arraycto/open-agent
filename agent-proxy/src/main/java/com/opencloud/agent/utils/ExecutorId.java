package com.opencloud.agent.utils;

import java.util.concurrent.atomic.AtomicLong;

public class ExecutorId {
    private static AtomicLong atomic = new AtomicLong(System.currentTimeMillis());

    public static synchronized long getExecutorId() {
        return atomic.incrementAndGet();
    }
}

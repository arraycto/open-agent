package com.opencloud.agent.concurrent;

public interface RejectedRunnable extends Runnable {

    void rejected();
}

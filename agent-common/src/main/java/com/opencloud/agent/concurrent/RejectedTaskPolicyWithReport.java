package com.opencloud.agent.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 如果当前任务实现了 {@link RejectedRunnable} 接口, 那么交给用户去实现拒绝任务的逻辑,
 * 否则以FIFO的方式抛弃队列中一部分现有任务.
 */
public class RejectedTaskPolicyWithReport extends AbstractRejectedExecutionHandler {

    public RejectedTaskPolicyWithReport(String threadPoolName) {
        super(threadPoolName, false, "");
    }

    public RejectedTaskPolicyWithReport(String threadPoolName, String dumpPrefixName) {
        super(threadPoolName, true, dumpPrefixName);
    }

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        logger.error("Thread pool [{}] is exhausted! {}.", threadPoolName, e.toString());

        dumpJvmInfoIfNeeded();

        if (r instanceof RejectedRunnable) {
            // 交给用户来处理
            ((RejectedRunnable) r).rejected();
        } else {
            if (!e.isShutdown()) {
                BlockingQueue<Runnable> queue = e.getQueue();
                int discardSize = queue.size() >> 1;
                for (int i = 0; i < discardSize; i++) {
                    queue.poll();
                }

                try {
                    queue.put(r);
                } catch (InterruptedException ignored) { /* should not be interrupted */ }
            }
        }
    }
}

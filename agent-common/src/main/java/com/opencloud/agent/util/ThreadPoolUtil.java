package com.opencloud.agent.util;

import com.opencloud.agent.concurrent.RejectedTaskPolicyWithReport;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 新建状态，就绪状态，运行状态，阻塞状态，死亡状态
 * <p>
 * AbortPolicy         -- 它将抛出 RejectedExecutionException 异常。
 * CallerRunsPolicy    -- 会在线程池当前正在运行的Thread线程池中处理被拒绝的任务。
 * DiscardOldestPolicy -- 线程池会放弃等待队列中最旧的未处理任务，然后将被拒绝的任务添加到等待队列中。
 * DiscardPolicy       -- 线程池将丢弃被拒绝的任务。
 * <p>
 * RejectedTaskPolicyWithReport --自定义策略
 */
public class ThreadPoolUtil {
    public static ThreadPoolExecutor makeServerThreadPool(final String serviceName, int corePoolSize, int maxPoolSize) {
        String name = "StarAgent-" + serviceName;
        return new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(100),
                r -> new Thread(r, name + "-" + r.hashCode()),
                new RejectedTaskPolicyWithReport(name + "-Policy", name));
    }
}

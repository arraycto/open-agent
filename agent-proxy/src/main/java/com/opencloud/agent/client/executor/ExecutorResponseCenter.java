package com.opencloud.agent.client.executor;

import cn.hutool.core.bean.BeanUtil;
import com.opencloud.agent.executor.ExecutorResult;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ThreadPoolExecutor;

public class ExecutorResponseCenter {

    private final ThreadPoolExecutor threadPoolExecutor;
    private final Integer retryTime;

    public ExecutorResponseCenter(ThreadPoolExecutor threadPoolExecutor, Integer retryTime) {
        this.threadPoolExecutor = threadPoolExecutor;
        this.retryTime = retryTime;
    }

    public void executorCenter(Object msg) {
        // 类加载器序号忽略：
        // msg 返回来的结果属于 Laucher$AppClassLoader@8089
        // 而该项目类加载器是 RestartClassLoader@8025
        // 所以instanceof无法判断类型，曲线做法如下
        if (msg instanceof String) {
            return;
        }
        ExecutorResult executorResult = BeanUtil.toBean(msg, ExecutorResult.class);
        if (StringUtils.isBlank(executorResult.getClientId())) {
            return;
        }
        ExecutorResponseHandler eh = new ExecutorResponseHandler(executorResult);
        threadPoolExecutor.execute(eh);

    }

}

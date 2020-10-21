package com.opencloud.agent.executor;

import com.opencloud.agent.ws.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ExecutorListener {

    @Async
    @EventListener
    public void executorListener(ExecutorEvent<List<ExecutorFromBrower>> event) {
        List<ExecutorFromBrower> executorFromBrowers = event.getSource();
        for (ExecutorFromBrower executorFromBrower : executorFromBrowers) {
            // 执行信息
            ExecutorCenter.setExecutor(executorFromBrower.getExecuteId(), executorFromBrower);
            WebSocketServer browserWsInfo = ExecutorCenter.getWebsocket(executorFromBrower.getSessionId());
            if (browserWsInfo != null) {
                ExecutorCenter.executor(browserWsInfo, executorFromBrower);
            }
        }
    }

}

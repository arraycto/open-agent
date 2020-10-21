package com.opencloud.agent.service;

import com.opencloud.agent.ExecutorStatus;
import com.opencloud.agent.executor.ExecutorBrowerForm;
import com.opencloud.agent.executor.ExecutorEvent;
import com.opencloud.agent.executor.ExecutorFromBrower;
import com.opencloud.agent.utils.ExecutorId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WebService {

    @Autowired
    private ApplicationContext applicationContext;

    public void executor(ExecutorBrowerForm browerForm, String fromIpAddr) {
        List<String> clientIds = browerForm.getClientIds();
        String command = browerForm.getCommand();
        String sessionId = browerForm.getSessionId();
        if (clientIds == null || clientIds.size() == 0) {
            return;
        }
        List<ExecutorFromBrower> executorFromBrowers = new ArrayList<>();
        for (String clientId : clientIds) {
            long executeId = ExecutorId.getExecutorId();
            ExecutorFromBrower executorFromBrower = new ExecutorFromBrower();
            executorFromBrower.setStatus(ExecutorStatus.STATUS_NO);
            executorFromBrower.setExecuteId(executeId);
            executorFromBrower.setCommand(command);
            executorFromBrower.setSessionId(sessionId);
            executorFromBrower.setClientId(clientId);
            executorFromBrower.setBrowerIp(fromIpAddr);
            executorFromBrower.setIsExecutor(true);

            executorFromBrowers.add(executorFromBrower);
        }
        applicationContext.publishEvent(new ExecutorEvent<List<ExecutorFromBrower>>(executorFromBrowers));
    }

    public void stop(ExecutorBrowerForm browerForm, String fromIpAddr) {
        List<ExecutorFromBrower> executorFromBrowers = new ArrayList<>();
        List<String> clientIds = browerForm.getClientIds();
        Long executeId = browerForm.getExecuteId();
        String sessionId = browerForm.getSessionId();
        for (String clientId : clientIds) {
            ExecutorFromBrower executorFromBrower = new ExecutorFromBrower();
            executorFromBrower.setStatus(ExecutorStatus.STATUS_NO);
            executorFromBrower.setExecuteId(executeId);
            executorFromBrower.setCommand("stop");
            executorFromBrower.setSessionId(sessionId);
            executorFromBrower.setClientId(clientId);
            executorFromBrower.setBrowerIp(fromIpAddr);
            executorFromBrower.setIsExecutor(false);

            executorFromBrowers.add(executorFromBrower);
        }
        applicationContext.publishEvent(new ExecutorEvent<List<ExecutorFromBrower>>(executorFromBrowers));
    }
}

package com.opencloud.agent.task;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.opencloud.agent.codec.SocketInfo;
import com.opencloud.agent.entity.ScheduleJobClientEntity;
import com.opencloud.agent.entity.ScheduleJobEntity;
import com.opencloud.agent.entity.ScheduleJobExecutorLogEntity;
import com.opencloud.agent.executor.ExecutorFromProxy;
import com.opencloud.agent.redis.RedisUtils;
import com.opencloud.agent.service.ScheduleJobClientService;
import com.opencloud.agent.service.ScheduleJobExecutorLogService;
import com.opencloud.agent.service.ScheduleJobService;
import com.opencloud.agent.sink.SourceMessage;
import com.opencloud.agent.util.IpUtils;
import com.opencloud.agent.utils.ExecutorId;
import com.opencloud.config.MessageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

import static com.opencloud.agent.ExecutorStatus.STATUS_NO;
import static com.opencloud.agent.page.Constant.ResultStatus.*;

@Slf4j
@Component
public class CommandTask implements ITask {

    @Autowired
    private ScheduleJobClientService jobClientService;

    @Autowired
    private ScheduleJobExecutorLogService jobExecutorLogService;

    @Autowired
    private ScheduleJobService scheduleJobService;

    @Autowired
    private RedisUtils redisUtils;


    @Override
    public void run(Long logId, Long jobId, String command) {
        ScheduleJobEntity scheduleJob = scheduleJobService.getById(jobId);
        List<ScheduleJobClientEntity> jobClientEntities = jobClientService.list(new QueryWrapper<ScheduleJobClientEntity>()
                .eq("job_id", jobId));
        for (ScheduleJobClientEntity jobClientEntity : jobClientEntities) {
            //任务开始时间
            long startTime = System.currentTimeMillis();
            //数据库保存发送记录
            long executorId = ExecutorId.getExecutorId();
            ScheduleJobExecutorLogEntity logEntity = new ScheduleJobExecutorLogEntity();
            logEntity.setLogId(logId);
            logEntity.setBeanName(scheduleJob.getBeanName());
            logEntity.setParams(scheduleJob.getParams());
            logEntity.setCreateTime(new Date());
            logEntity.setExecutorId(executorId);
            logEntity.setStatus(JOB_STATUS_WAIT.getValue());
            logEntity.setTimes(0);
            jobExecutorLogService.save(logEntity);
            try {
                String clientId = jobClientEntity.getClientId();
                SocketInfo socketInfo = redisUtils.get(clientId, SocketInfo.class);
                if (socketInfo == null) {
                    logEntity.setStatus(JOB_STATUS_ERROR.getValue());
                    logEntity.setResult(clientId + ",已断开连接，请重试");
                } else {
                    //发送任务
                    log.debug("任务准备发送，任务ID：" + scheduleJob.getJobId());
                    ExecutorFromProxy executorFromProxy = new ExecutorFromProxy();
                    executorFromProxy.setType(MessageType.MESSAGE.getType());
                    executorFromProxy.setCommand(command);
                    executorFromProxy.setExecuteStatus(STATUS_NO);
                    executorFromProxy.setExecuteId(executorId);
                    executorFromProxy.setClientId(clientId);
                    executorFromProxy.setExecuteTime(System.currentTimeMillis());
                    executorFromProxy.setProxyIp(IpUtils.getLocalIp());
                    executorFromProxy.setAgentChannelId(socketInfo.getChannelId());
                    executorFromProxy.setExecutor(true);

                    SourceMessage sourceMessage = new SourceMessage();
                    sourceMessage.setChannelHost(socketInfo.getServerIp());
                    sourceMessage.setChannelPort(socketInfo.getServerPort());
                    sourceMessage.setExecutorFromProxy(executorFromProxy);

                    CommandFactory.getDefaultCommandRunner().put(sourceMessage);
                    //任务发送总时长
                    long times = System.currentTimeMillis() - startTime;
                    //任务状态   -1：待发送  0：发送成功  1：失败
                    logEntity.setStatus(JOB_STATUS_SUCCESS.getValue());

                    log.debug("任务发送完毕，任务ID：" + scheduleJob.getJobId() + "  总共耗时：" + times + "毫秒");
                }
            } catch (Exception e) {
                log.error("任务发送失败，任务ID：" + scheduleJob.getJobId(), e);
                //任务发送总时长
                long times = System.currentTimeMillis() - startTime;
                logEntity.setTimes((int) times);
                //任务状态    0：成功    1：失败
                logEntity.setStatus(JOB_STATUS_ERROR.getValue());
                logEntity.setResult(StrUtil.sub(e.toString(), 0, 2000));
                jobExecutorLogService.updateById(logEntity);
            }
        }
    }
}

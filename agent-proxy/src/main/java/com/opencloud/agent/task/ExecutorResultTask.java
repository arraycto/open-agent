package com.opencloud.agent.task;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.opencloud.agent.dao.ScheduleJobExecutorDetailLogDao;
import com.opencloud.agent.entity.ScheduleJobExecutorDetailLogEntity;
import com.opencloud.agent.entity.ScheduleJobExecutorLogEntity;
import com.opencloud.agent.executor.ExecutorResult;
import com.opencloud.agent.redis.RedisUtils;
import com.opencloud.agent.service.ScheduleJobExecutorLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.opencloud.agent.BasicConfigurationConstants.EXECUTOR_RESULT;
import static com.opencloud.agent.ExecutorStatus.STATUS_FINISH;
import static com.opencloud.agent.page.Constant.ResultStatus.JOB_STATUS_SUCCESS;

@Slf4j
@Component
public class ExecutorResultTask {
    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ScheduleJobExecutorLogService jobExecutorLogService;

    @Autowired
    private ScheduleJobExecutorDetailLogDao jobExecutorDetailLogDao;

    /**
     * 处理订单导入
     */
    @Scheduled(fixedRate = 1000)
    private void processExecutorResultImport() {
        ScheduleJobExecutorLogEntity jobExecutorLogEntity = null;
        try {
            Object object = redisUtils.pop(EXECUTOR_RESULT, 1, TimeUnit.SECONDS);
            if (null == object) {
                return;
            }
            String msg = (String) object;
            ExecutorResult executorResult = JSON.parseObject(msg, ExecutorResult.class);
            if (executorResult == null) {
                return;
            }
            if (StringUtils.isBlank(executorResult.getResult())) {
                executorResult.setResult(executorResult.getExecuteStatus().getMessage());
            }
            Long executeId = executorResult.getExecuteId();
            if (executeId == null) {
                return;
            }
            jobExecutorLogEntity = jobExecutorLogService.getByExecutorId(executeId);
            if (jobExecutorLogEntity == null) {
                return;
            }
            jobExecutorLogEntity.setExecuteTime(executorResult.getExecuteTime());
            jobExecutorLogEntity.setResult(msg);
            jobExecutorLogEntity.setCreateTime(new Date());
            if (STATUS_FINISH.getStatus() == executorResult.getExecuteStatus().getStatus()) {
                //任务发送总时长
                long times = System.currentTimeMillis() - jobExecutorLogEntity.getCreateTime().getTime();
                jobExecutorLogEntity.setTimes((int) times);
                //任务状态   -1：待发送  0：发送成功  1：失败
                jobExecutorLogEntity.setStatus(JOB_STATUS_SUCCESS.getValue());
                log.info("任务执行完毕，任务ID：{},执行ID:{}, 总共耗时：{} 毫秒", executorResult.getExecuteId(), executorResult.getExecuteId(), times);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (jobExecutorLogEntity != null) {
                jobExecutorLogService.updateById(jobExecutorLogEntity);
                // detail
                ScheduleJobExecutorDetailLogEntity detailLogEntity = BeanUtil.toBean(jobExecutorLogEntity, ScheduleJobExecutorDetailLogEntity.class);
                jobExecutorDetailLogDao.insert(detailLogEntity);
            }
        }
    }
}



package com.opencloud.agent.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.opencloud.agent.entity.ScheduleJobEntity;
import com.opencloud.agent.entity.ScheduleJobLogEntity;
import com.opencloud.agent.service.ScheduleJobLogService;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.Method;
import java.util.Date;

import static com.opencloud.agent.page.Constant.ResultStatus.*;


/**
 * 定时任务
 *
 * @author xielianjun
 */
public class ScheduleJob extends QuartzJobBean {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void executeInternal(JobExecutionContext context) {
        Object o = context.getMergedJobDataMap()
                .get(ScheduleJobEntity.JOB_PARAM_KEY);
        ScheduleJobEntity scheduleJob = BeanUtil.toBean(o, ScheduleJobEntity.class);
        //获取spring bean
        ScheduleJobLogService scheduleJobLogService = (ScheduleJobLogService) SpringContextUtils.getBean("scheduleJobLogService");

        //数据库保存执行记录
        ScheduleJobLogEntity log = new ScheduleJobLogEntity();
        log.setJobId(scheduleJob.getJobId());
        log.setBeanName(scheduleJob.getBeanName());
        log.setParams(scheduleJob.getParams());
        log.setCreateTime(new Date());
        log.setTimes(0);
        log.setStatus(JOB_STATUS_WAIT.getValue());
        scheduleJobLogService.save(log);
        //任务开始时间
        long startTime = System.currentTimeMillis();

        try {
            //执行任务
            logger.debug("任务准备执行，任务ID：" + scheduleJob.getJobId());

            Object target = SpringContextUtils.getBean(scheduleJob.getBeanName());
            Method method = target.getClass().getDeclaredMethod("run", Long.class, Long.class, String.class);
            method.invoke(target, log.getLogId(), scheduleJob.getJobId(), scheduleJob.getParams());

            //任务执行总时长
            long times = System.currentTimeMillis() - startTime;
            log.setTimes((int) times);
            //任务状态,0：发送成功    1：发送失败
            log.setStatus(JOB_STATUS_SUCCESS.getValue());

            logger.debug("任务执行完毕，任务ID：" + scheduleJob.getJobId() + "  总共耗时：" + times + "毫秒");
        } catch (Exception e) {
            logger.error("任务执行失败，任务ID：" + scheduleJob.getJobId(), e);

            //任务执行总时长
            long times = System.currentTimeMillis() - startTime;
            log.setTimes((int) times);

            //任务状态    0：成功    1：失败
            log.setStatus(JOB_STATUS_ERROR.getValue());
            log.setError(StrUtil.sub(e.toString(), 0, 2000));
        } finally {
            scheduleJobLogService.updateById(log);
        }
    }
}

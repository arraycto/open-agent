

package com.opencloud.agent.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.opencloud.agent.entity.ScheduleJobExecutorLogEntity;
import com.opencloud.agent.page.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 定时任务
 *
 * @author xielianjun
 */
public interface ScheduleJobExecutorLogService extends IService<ScheduleJobExecutorLogEntity> {

    PageUtils queryPage(Map<String, Object> params);

    ScheduleJobExecutorLogEntity getByExecutorId(Long executorId);

    List<ScheduleJobExecutorLogEntity> getByJobId(Long jobId);
}

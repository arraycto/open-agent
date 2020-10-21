

package com.opencloud.agent.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.opencloud.agent.entity.ScheduleJobLogEntity;
import com.opencloud.agent.page.PageUtils;

import java.util.Map;

/**
 * 定时任务日志
 *
 * @author xielianjun
 */
public interface ScheduleJobLogService extends IService<ScheduleJobLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

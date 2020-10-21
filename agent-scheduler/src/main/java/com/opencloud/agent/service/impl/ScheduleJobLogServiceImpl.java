

package com.opencloud.agent.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.opencloud.agent.dao.ScheduleJobLogDao;
import com.opencloud.agent.entity.ScheduleJobLogEntity;
import com.opencloud.agent.page.PageUtils;
import com.opencloud.agent.page.Query;
import com.opencloud.agent.service.ScheduleJobLogService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author xielianjun
 */
@Service("scheduleJobLogService")
public class ScheduleJobLogServiceImpl extends ServiceImpl<ScheduleJobLogDao, ScheduleJobLogEntity> implements ScheduleJobLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String jobId = (String) params.get("jobId");

        IPage<ScheduleJobLogEntity> page = this.page(
                new Query<ScheduleJobLogEntity>().getPage(params, "create_time", false),
                new QueryWrapper<ScheduleJobLogEntity>().like(StrUtil.isNotBlank(jobId), "job_id", jobId)
        );

        return new PageUtils(page);
    }

}

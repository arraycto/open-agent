

package com.opencloud.agent.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.opencloud.agent.dao.ScheduleJobExecutorDetailLogDao;
import com.opencloud.agent.dao.ScheduleJobExecutorLogDao;
import com.opencloud.agent.entity.ScheduleJobExecutorLogEntity;
import com.opencloud.agent.page.PageUtils;
import com.opencloud.agent.page.Query;
import com.opencloud.agent.service.ScheduleJobExecutorLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author xielianjun
 */
@Service("scheduleJobExecutorLogService")
public class ScheduleJobExecutorLogServiceImpl extends ServiceImpl<ScheduleJobExecutorLogDao, ScheduleJobExecutorLogEntity> implements ScheduleJobExecutorLogService {

    @Autowired
    private ScheduleJobExecutorDetailLogDao jobExecutorDetailLogDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String logId = (String) params.get("logId");
        String executorId = (String) params.get("executorId");

        IPage<ScheduleJobExecutorLogEntity> page = this.page(
                new Query<ScheduleJobExecutorLogEntity>().getPage(params, "create_time", false),
                new QueryWrapper<ScheduleJobExecutorLogEntity>()
                        .eq(StrUtil.isNotBlank(logId), "log_id", logId)
                        .eq(StrUtil.isNotBlank(executorId), "executor_id", executorId)
        );
        page.getRecords().forEach(e -> {
            Long tmpExeId = e.getExecutorId();
            e.setDetailList(jobExecutorDetailLogDao.selectByExecutorId(tmpExeId));

        });
        return new PageUtils(page);
    }

    @Override
    public ScheduleJobExecutorLogEntity getByExecutorId(Long executorId) {
        return getOne(new QueryWrapper<ScheduleJobExecutorLogEntity>().eq("executor_id", executorId));
    }

    @Override
    public List<ScheduleJobExecutorLogEntity> getByJobId(Long jobId) {
        return list(new QueryWrapper<ScheduleJobExecutorLogEntity>().eq("job_id", jobId));
    }

}

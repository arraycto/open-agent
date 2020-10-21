

package com.opencloud.agent.controller;

import com.opencloud.agent.entity.ScheduleJobExecutorLogEntity;
import com.opencloud.agent.page.PageUtils;
import com.opencloud.agent.page.R;
import com.opencloud.agent.service.ScheduleJobExecutorLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 定时任务日志
 *
 * @author xielianjun
 */
@RestController
@RequestMapping("executor/log")
public class ScheduleJobExecutorLogController {

    @Autowired
    private ScheduleJobExecutorLogService scheduleJobExecutorLogService;

    /**
     * 定时任务日志列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = scheduleJobExecutorLogService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 定时任务日志信息
     */
    @RequestMapping("/info/{logId}")
    public R info(@PathVariable("logId") Long logId) {
        ScheduleJobExecutorLogEntity log = scheduleJobExecutorLogService.getById(logId);

        return R.ok().put("log", log);
    }

    /**
     * 定时任务日志信息
     */
    @RequestMapping("/{jobId}")
    public R jobId(@PathVariable("jobId") Long jobId) {
        List<ScheduleJobExecutorLogEntity> logs = scheduleJobExecutorLogService.getByJobId(jobId);

        return R.ok().put("logs", logs);
    }

    /**
     * 定时任务日志信息
     */
    @RequestMapping("/{executorId}")
    public R executor(@PathVariable("executorId") Long executorId) {
        ScheduleJobExecutorLogEntity log = scheduleJobExecutorLogService.getByExecutorId(executorId);

        return R.ok().put("log", log);
    }
}

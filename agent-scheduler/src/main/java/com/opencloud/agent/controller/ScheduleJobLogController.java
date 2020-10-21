

package com.opencloud.agent.controller;

import com.opencloud.agent.entity.ScheduleJobLogEntity;
import com.opencloud.agent.page.PageUtils;
import com.opencloud.agent.page.R;
import com.opencloud.agent.service.ScheduleJobLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 定时任务日志
 *
 * @author xielianjun
 */
@RestController
@RequestMapping("sys/scheduleLog")
public class ScheduleJobLogController {

    @Autowired
    private ScheduleJobLogService scheduleJobLogService;

    /**
     * 定时任务日志列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = scheduleJobLogService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 定时任务日志信息
     */
    @RequestMapping("/info/{logId}")
    public R info(@PathVariable("logId") Long logId) {
        ScheduleJobLogEntity log = scheduleJobLogService.getById(logId);

        return R.ok().put("log", log);
    }

}

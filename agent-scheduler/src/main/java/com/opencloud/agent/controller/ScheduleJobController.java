

package com.opencloud.agent.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.opencloud.agent.anno.SysLog;
import com.opencloud.agent.entity.ScheduleJobClientEntity;
import com.opencloud.agent.entity.ScheduleJobEntity;
import com.opencloud.agent.page.PageUtils;
import com.opencloud.agent.page.R;
import com.opencloud.agent.service.ScheduleJobClientService;
import com.opencloud.agent.service.ScheduleJobService;
import com.opencloud.agent.utils.RequestIpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 定时任务
 *
 * @author xielianjun
 */
@RestController
@RequestMapping("sys/schedule")
public class ScheduleJobController {

    @Autowired
    private ScheduleJobService scheduleJobService;

    @Autowired
    private ScheduleJobClientService jobClientService;

    /**
     * 定时任务列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = scheduleJobService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 定时任务信息
     */
    @RequestMapping("/info/{jobId}")
    public R info(@PathVariable("jobId") Long jobId) {
        ScheduleJobEntity scheduleJob = scheduleJobService.getById(jobId);
        List<ScheduleJobClientEntity> jobClientEntities = jobClientService.list(new QueryWrapper<ScheduleJobClientEntity>().eq("job_id", scheduleJob.getJobId()));
        scheduleJob.setClientIds(jobClientEntities.stream().map(ScheduleJobClientEntity::getClientId).collect(Collectors.toList()));
        return R.ok().put("schedule", scheduleJob);
    }

    /**
     * 保存定时任务
     */
    @SysLog("保存定时任务")
    @RequestMapping("/save")
    public R save(@RequestBody ScheduleJobEntity scheduleJob, HttpServletRequest request) {

        scheduleJobService.saveJob(scheduleJob, RequestIpUtils.getIpAddr(request));

        return R.ok();
    }

    /**
     * 修改定时任务
     */
    @SysLog("修改定时任务")
    @RequestMapping("/update")
    public R update(@RequestBody ScheduleJobEntity scheduleJob, HttpServletRequest request) {

        scheduleJobService.update(scheduleJob, RequestIpUtils.getIpAddr(request));

        return R.ok();
    }

    /**
     * 删除定时任务
     */
    @SysLog("删除定时任务")
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] jobIds) {
        scheduleJobService.deleteBatch(jobIds);

        return R.ok();
    }

    /**
     * 立即执行任务
     */
    @SysLog("立即执行任务")
    @RequestMapping("/run")
    public R run(@RequestBody Long[] jobIds) {
        scheduleJobService.run(jobIds);

        return R.ok();
    }

    /**
     * 暂停定时任务
     */
    @SysLog("暂停定时任务")
    @RequestMapping("/pause")
    public R pause(@RequestBody Long[] jobIds) {
        scheduleJobService.pause(jobIds);

        return R.ok();
    }

    /**
     * 恢复定时任务
     */
    @SysLog("恢复定时任务")
    @RequestMapping("/resume")
    public R resume(@RequestBody Long[] jobIds) {
        scheduleJobService.resume(jobIds);

        return R.ok();
    }

}

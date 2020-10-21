

package com.opencloud.agent.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.opencloud.agent.dao.ScheduleJobDao;
import com.opencloud.agent.entity.ScheduleJobClientEntity;
import com.opencloud.agent.entity.ScheduleJobEntity;
import com.opencloud.agent.page.Constant;
import com.opencloud.agent.page.PageUtils;
import com.opencloud.agent.page.Query;
import com.opencloud.agent.service.ScheduleJobClientService;
import com.opencloud.agent.service.ScheduleJobService;
import com.opencloud.agent.utils.ScheduleUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @author xielianjun
 */
@Service("scheduleJobService")
public class ScheduleJobServiceImpl extends ServiceImpl<ScheduleJobDao, ScheduleJobEntity> implements ScheduleJobService {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private ScheduleJobClientService clientService;

    /**
     * 项目启动时，初始化定时器
     * 从Java EE5规范开始，Servlet增加了两个影响Servlet生命周期的注解（Annotation）：@PostConstruct和@PreConstruct。
     * 这两个注解被用来修饰一个非静态的void()方法.而且这个方法不能有抛出异常声明。
     */
    @PostConstruct
    public void init() {
        List<ScheduleJobEntity> scheduleJobList = this.list();
        for (ScheduleJobEntity scheduleJob : scheduleJobList) {
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobId());
            //如果不存在，则创建
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            } else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
        }
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String beanName = (String) params.get("beanName");

        IPage<ScheduleJobEntity> page = this.page(
                new Query<ScheduleJobEntity>().getPage(params, "create_time", false),
                new QueryWrapper<ScheduleJobEntity>().like(StrUtil.isNotBlank(beanName), "bean_name", beanName)
        );

        return new PageUtils(page);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveJob(ScheduleJobEntity scheduleJob, String ipAddr) {
        scheduleJob.setCreateTime(new Date());
        scheduleJob.setStatus(Constant.ScheduleStatus.NORMAL.getValue());
        this.save(scheduleJob);
        List<String> clientIdList = scheduleJob.getClientIds();
        List<ScheduleJobClientEntity> list = new ArrayList<>();
        for (String clientId : clientIdList) {
            ScheduleJobClientEntity jobClientEntity = new ScheduleJobClientEntity();
            jobClientEntity.setJobId(scheduleJob.getJobId());
            jobClientEntity.setClientId(clientId);
            jobClientEntity.setBrowserIp(ipAddr);
            jobClientEntity.setStatus(scheduleJob.getStatus());
            list.add(jobClientEntity);
        }
        clientService.saveBatch(list);
        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ScheduleJobEntity scheduleJob, String ipAddr) {
        ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);

        this.updateById(scheduleJob);
        clientService.remove(new QueryWrapper<ScheduleJobClientEntity>().eq("job_id", scheduleJob.getJobId()));
        List<String> clientIdList = scheduleJob.getClientIds();
        List<ScheduleJobClientEntity> list = new ArrayList<>();
        for (String clientId : clientIdList) {
            ScheduleJobClientEntity jobClientEntity = new ScheduleJobClientEntity();
            jobClientEntity.setJobId(scheduleJob.getJobId());
            jobClientEntity.setClientId(clientId);
            jobClientEntity.setBrowserIp(ipAddr);
            jobClientEntity.setStatus(scheduleJob.getStatus());
            list.add(jobClientEntity);
        }
        clientService.saveBatch(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] jobIds) {
        for (Long jobId : jobIds) {
            ScheduleUtils.deleteScheduleJob(scheduler, jobId);
        }

        //删除数据
        this.removeByIds(Arrays.asList(jobIds));
    }

    @Override
    public int updateBatch(Long[] jobIds, int status) {
        for (Long jobId : jobIds) {
            ScheduleJobEntity scheduleJobEntity = new ScheduleJobEntity();
            scheduleJobEntity.setJobId(jobId);
            scheduleJobEntity.setStatus(status);
            updateById(scheduleJobEntity);
        }
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void run(Long[] jobIds) {
        for (Long jobId : jobIds) {
            ScheduleUtils.run(scheduler, this.getById(jobId));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pause(Long[] jobIds) {
        for (Long jobId : jobIds) {
            ScheduleUtils.pauseJob(scheduler, jobId);
        }

        updateBatch(jobIds, Constant.ScheduleStatus.PAUSE.getValue());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resume(Long[] jobIds) {
        for (Long jobId : jobIds) {
            ScheduleUtils.resumeJob(scheduler, jobId);
        }

        updateBatch(jobIds, Constant.ScheduleStatus.NORMAL.getValue());
    }

}



package com.opencloud.agent.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.opencloud.agent.entity.ScheduleJobExecutorDetailLogEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 定时任务日志
 *
 * @author xielianjun
 */
@Mapper
public interface ScheduleJobExecutorDetailLogDao extends BaseMapper<ScheduleJobExecutorDetailLogEntity> {

    @Select("select * from schedule_job_executor_detail_log where executor_id = #{executorId}")
    List<ScheduleJobExecutorDetailLogEntity> selectByExecutorId(Long executorId);
}

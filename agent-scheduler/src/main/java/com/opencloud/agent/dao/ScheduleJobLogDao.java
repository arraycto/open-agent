

package com.opencloud.agent.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.opencloud.agent.entity.ScheduleJobLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 定时任务日志
 *
 * @author xielianjun
 */
@Mapper
public interface ScheduleJobLogDao extends BaseMapper<ScheduleJobLogEntity> {

}



package com.opencloud.agent.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 定时任务日志
 *
 * @author xielianjun
 */
@Data
@TableName("schedule_job_executor_detail_log")
public class ScheduleJobExecutorDetailLogEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 执行id
     */
    @TableId(type = IdType.AUTO)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private Long executorId;

    /**
     * 日志id
     */
    private Long logId;

    /**
     * spring bean名称
     */
    private String beanName;

    /**
     * 参数
     */
    private String params;

    /**
     * 任务状态  -1:待执行  0：成功    1：失败
     */
    private Integer status;

    /**
     * 信息
     */
    private String result;

    /**
     * 耗时(单位：毫秒)
     */
    private Integer times;

    private Long executeTime;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}

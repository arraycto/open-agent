

package com.opencloud.agent.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 定时任务
 *
 * @author xielianjun
 */
@Data
@TableName("schedule_job_client")
public class ScheduleJobClientEntity {

    /**
     * 任务id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long jobId;

    private String clientId;
    private String clientIp;
    private String browserIp;
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}

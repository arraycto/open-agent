package com.opencloud.agent.entity;

import lombok.Data;

/**
 * @author xielianjun
 * @email xielianjun@sensetime.com
 * @date 2019-07-03 14:34:39
 */
@Data
public class SysMailEntity {
    /**
     * 邮件主题
     */
    private String mailSubject;
    /**
     * 邮件内容
     */
    private String mailContent;

    private String to;

    private String cc;

}

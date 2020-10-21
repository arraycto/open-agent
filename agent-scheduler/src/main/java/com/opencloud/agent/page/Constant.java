

package com.opencloud.agent.page;

public class Constant {

    /**
     * 当前页码
     */
    public static final String PAGE = "page";
    /**
     * 每页显示记录数
     */
    public static final String LIMIT = "limit";
    /**
     * 排序字段
     */
    public static final String ORDER_FIELD = "sidx";
    /**
     * 排序方式
     */
    public static final String ORDER = "order";
    /**
     * 升序
     */
    public static final String ASC = "asc";


    /**
     * 定时任务状态
     *
     * @date 2016年12月3日 上午12:07:22
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 暂停
         */
        PAUSE(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * Result
     */
    public enum ResultStatus {
        /**
         * wait
         */
        JOB_STATUS_WAIT (-1),
        /**
         * 成功
         */
        JOB_STATUS_SUCCESS(0),
        /**
         * 失败
         */
        JOB_STATUS_ERROR(1);

        private int value;

        ResultStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

}

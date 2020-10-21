package com.opencloud.agent;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString
public class Result {

    public static final Integer FAIL_CODE = -1;
    public static final Integer SUCCESSFUL_CODE = 0;
    public static final String SUCCESSFUL_MEG = "处理成功";
    public static final String FAIL_MEG = "处理失败";

    private Integer code;
    private String msg;
    private Date time;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    private Result() {
        this.time = new Date();
    }

    /**
     * 内部使用，用于构造成功的结果
     *
     * @param code
     * @param msg
     * @param data
     */
    public Result(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.time = new Date();
    }

    /**
     * 快速创建成功结果并返回结果数据
     *
     * @param data
     * @return R
     */
    public static Result ok(Object data) {
        return new Result(SUCCESSFUL_CODE, SUCCESSFUL_MEG, data);
    }

    /**
     * 快速创建成功结果
     *
     * @return R
     */
    public static Result ok() {
        return ok(null);
    }

    /**
     * 系统异常类没有返回数据
     *
     * @return R
     */
    public static Result error(Object data) {
        return new Result(FAIL_CODE, FAIL_MEG, data);
    }

    /**
     * 系统异常类没有返回数据
     *
     * @return R
     */
    public static Result error() {
        return new Result(FAIL_CODE, FAIL_MEG, null);
    }

    /**
     * 成功code=000000
     *
     * @return true/false
     */
    @JsonIgnore
    public boolean isSuccess() {
        return SUCCESSFUL_CODE.equals(this.code);
    }

    /**
     * 失败
     *
     * @return true/false
     */
    @JsonIgnore
    public boolean isFail() {
        return !isSuccess();
    }

    public enum ResponseConstants {

        SUCCESS(0, "success."),

        FAILED(30000, "failed."),

        NOMOCKINFO(30100, "没有匹配的MOCK信息"),

        NORESULT(30200, "没有匹配的响应值"),

        EXPERROR(30300, "表达示不正确: "),

        EXCEPTION(500, "系统异常,请重试");

        private Integer code;

        private String msg;

        private ResponseConstants(Integer code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

    }
}

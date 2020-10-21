package com.opencloud.agent;

import cn.hutool.core.util.StrUtil;

import java.util.Formatter;

import static cn.hutool.core.util.StrUtil.DASHED;

public final class BasicConfigurationConstants {

    /**
     * 基础存储目录
     */
    public static final String BASE_STORE_PATH = "./config/";
    /**
     * 配置文件
     */
    public static final String ENGINE_CONFIG_NAME = "config.properties";
    public static final String ENGINE_CONFIG_SERVER_STORE_PATH = BASE_STORE_PATH + "server.properties";
    public static final String ENGINE_CONFIG_CLIENT_STORE_PATH = BASE_STORE_PATH + "client.properties";
    /**
     * 注册中心
     */
    public static final String CONFIG_REGISTER = "zookeeper";
    public static final String CONFIG_REGISTER_PREFIX = CONFIG_REGISTER + ".";
    /**
     * 目前启动的服务类型配置
     */
    public static final String CONFIG_SERVER = "agent";
    public static final String CONFIG_SERVER_PREFIX = CONFIG_SERVER + ".";
    /**
     * server类型type
     */
    public static final String CONFIG_TYPE = CONFIG_SERVER_PREFIX + "type";
    public static final String CONFIG_SERVER_NAME = CONFIG_SERVER_PREFIX + "name";
    public static final String CONFIG_SERVER_HOST = CONFIG_SERVER_PREFIX + "host";
    public static final String CONFIG_SERVER_PORT = CONFIG_SERVER_PREFIX + "port";
    /**
     * register host port
     */
    public static final String CONFIG_REGISTER_ADDRESS = CONFIG_REGISTER_PREFIX + "address";
    public static final String CONFIG_REGISTER_SCHEME = CONFIG_REGISTER_PREFIX + "scheme";
    public static final String CONFIG_REGISTER_AUTH = CONFIG_REGISTER_PREFIX + "auth";


    /**
     * service version
     */
    public static final String VERSION_1 = "1.0";
    /**
     * 服务环境变量配置
     */
    public static final String CLIENT_RETRY_TIME = "client.retry.time";
    /**
     * 换行符
     */
    public static final String NEWLINE;
    /**
     * proxy processor queue size
     */
    public static final String DEFAULT_PROXY_PROCESSOR_SIZE = "proxy.queue.size";
    /**
     * 连接初始化标示
     */
    private static final String ROOT_NAME = "Hessc";
    /**
     * 根服务,proxy代理服务使用
     */
    public static final String INIT_NAME_PREFIX_PATTERN = ROOT_NAME + StrUtil.COLON + "*";
    /**
     * root init server
     */
    private static final String INIT_NAME_PREFIX = ROOT_NAME + StrUtil.COLON;
    /**
     * channel服务使用
     */
    public static String AGENTN_SERVER = "channel";
    public static String AGENTN_NAME = INIT_NAME_PREFIX + AGENTN_SERVER;
    public static String AGENTN_NAME_REDIS_KEY = INIT_NAME_PREFIX + AGENTN_SERVER + StrUtil.COLON;
    public static String AGENTN_NAME_REDIS_KEY_ALL = INIT_NAME_PREFIX + AGENTN_SERVER + StrUtil.COLON + "*";
    /**
     * proxy服务使用
     */
    public static String PROXY_SERVER = ROOT_NAME + DASHED + "Proxy";
    public static String PROXY_NAME_REDIS_KEY = PROXY_SERVER + StrUtil.COLON;

    /**
     * 执行结果
     */
    public static String EXECUTOR_RESULT = ROOT_NAME + DASHED + "Executors";

    static {
        String newLine;
        try {
            newLine = new Formatter().format("%n").toString();
        } catch (Exception e) {
            newLine = "\n";
        }
        NEWLINE = newLine;
    }

    public static void setAgentnName(String agentnName) {
        AGENTN_NAME = INIT_NAME_PREFIX + agentnName;
        AGENTN_NAME_REDIS_KEY = INIT_NAME_PREFIX + agentnName + StrUtil.COLON;
        AGENTN_NAME_REDIS_KEY_ALL = INIT_NAME_PREFIX + agentnName + StrUtil.COLON + "*";
    }

}

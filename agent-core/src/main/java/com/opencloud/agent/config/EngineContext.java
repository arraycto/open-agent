package com.opencloud.agent.config;

import cn.hutool.core.io.FileUtil;
import com.opencloud.agent.BasicConfigurationConstants;
import com.opencloud.agent.util.FileUtils;
import com.opencloud.config.RegisterType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * EngineContext
 */
public class EngineContext {

    private static final Logger logger = LoggerFactory.getLogger(EngineContext.class);

    private static long bootTime = System.currentTimeMillis();

    /**
     * 应用程序初始化,只被调用一次即可
     *
     * @param server
     */
    public static void applicationInitialization(RegisterType server) {
        logger.info("Engine context applicationInitialization...");

        bootTime = System.currentTimeMillis();
        // agent配置信息
        applicationProptiesInit(server);

        logger.info("Engine context applicationInitialization success!");
    }


    /**
     * 获取启动时间
     *
     * @return long
     */
    public static long getBootTime() {
        return bootTime;
    }

    private static void applicationProptiesInit(RegisterType server) {
        String dstPath = server.getStoreName();
        if (!FileUtil.exist(dstPath) || FileUtil.size(FileUtil.newFile(dstPath)) <= 0) {
            FileUtil.del(dstPath);
            FileUtils.extractFileToDisk(BasicConfigurationConstants.ENGINE_CONFIG_NAME, dstPath);
        }
    }
}

package com.opencloud.agent.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 读取资源文件内容
     *
     * @param resourcePath resourcePath
     * @return String
     */
    public static String readFileToString(String resourcePath) {
        InputStream in = null;
        try {
            in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath);
            return new String(IoUtil.readBytes(in, in != null ? in.available() : 0), StandardCharsets.UTF_8);

        } catch (Exception e) {
            logger.error("Load resource conf {} failed! Error is: {}!", resourcePath, e.getMessage());
            return "";
        } finally {
            IoUtil.close(in);
        }
    }

    public static boolean extractFileToDisk(String resourcePath, String diskPath) {
        return extractFileToDisk(resourcePath, diskPath, false);
    }

    /**
     * 提取资源文件
     *
     * @param resourcePath resourcePath
     * @param diskPath     distPath
     * @return boolean
     */
    public static boolean extractFileToDisk(String resourcePath, String diskPath, boolean cover) {
        if (StrUtil.isBlank(resourcePath) || StrUtil.isBlank(diskPath)) {
            logger.error("Path parameter is invalid!");
            return false;
        }
        InputStream in = null;
        OutputStream out = null;
        try {
            // check file
            diskPath = new File(diskPath).getCanonicalPath();
            ensureDirExist(diskPath.substring(0, diskPath.lastIndexOf(File.separatorChar)));

            File target = new File(diskPath);
            if (!cover) {
                if (target.exists()) {
                    logger.info("File {} already exist, will skip...", diskPath);
                    return true;
                } else {
                    if (!target.createNewFile()) {
                        logger.error("Create file failed {}!", diskPath);
                    }
                }
            }
            // copy file
            in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath);
            if (in == null) {
                return false;
            }
            out = new FileOutputStream(diskPath);
            IoUtil.copy(in, out);

            return true;
        } catch (Exception e) {
            logger.error("extractFileToDisk failed! {}", e.getMessage());
            return false;
        } finally {
            IoUtil.close(out);
            IoUtil.close(in);
        }
    }

    private static void ensureDirExist(String path) {
        if (!FileUtil.exist(path)) {
            File file = new File(path);
            file.mkdirs();
        }
    }

}

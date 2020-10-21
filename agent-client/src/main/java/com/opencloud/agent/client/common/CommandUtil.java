package com.opencloud.agent.client.common;

import cn.hutool.core.util.StrUtil;
import com.opencloud.agent.ExecutorStatus;
import com.opencloud.agent.client.executor.ExecutorRequestHandler;
import com.opencloud.agent.executor.ExecutorResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class CommandUtil {

    private static final String OS_NAME = System.getProperty("os.name");
    private static final String WIN = "win";
    private static final String CMD = "cmd";
    private static final String BAT = "bat";
    private static Map<Long, DefaultExecutor> defaultExecutorHashMap = new HashMap<>();
    private static String charset = "UTF-8";

    public static void executeCommand(String command, String args, ExecutorResult result, boolean isExecutor, ExecutorRequestHandler.CmdCallBack cmdCallBack) {
        Long executeId = result.getExecuteId();
        if (executeId == null) {
            return;
        }
        try {
            if (!isExecutor) {
                DefaultExecutor defaultExecutor = defaultExecutorHashMap.get(executeId);
                if (defaultExecutor != null) {
                    ExecuteWatchdog watchdog = defaultExecutor.getWatchdog();
                    if (watchdog != null) {
                        watchdog.destroyProcess();
                    }
                }
            } else {
                doExecute(command, args, result, cmdCallBack, executeId);
                defaultExecutorHashMap.remove(executeId);
            }
        } catch (IOException e) {
            log.error("程序狗执行报错: {}", e.getMessage());
            result.setResult(e.getMessage());
            sendErrorLog(cmdCallBack, result);
            defaultExecutorHashMap.remove(executeId);
        }
    }

    private static void doExecute(String command, String args, ExecutorResult result, ExecutorRequestHandler.CmdCallBack cmdCallBack, Long executeId) throws IOException {
        CommandLine commandLine;
        if (OS.isFamilyUnix()) {
            commandLine = CommandLine.parse("/bin/sh");
            commandLine.addArgument("-c");
            commandLine.addArgument(command, false);
        } else {
            commandLine = CommandLine.parse(command);
        }
        if (StrUtil.isNotBlank(args)) {
            //参数以空格进行分割，如果有参数中确实带有空格的，则加上转义符号\
            String[] argss = args.split("(?<!\\\\)\\s+");
            commandLine.addArguments(argss);
        }
        DefaultExecutor exec = new DefaultExecutor();
        // watchDog
        exec.setWatchdog(new ExecuteWatchdog(ExecuteWatchdog.INFINITE_TIMEOUT));
        exec.setExitValues(null);
        defaultExecutorHashMap.put(executeId, exec);
        // endDog
        if (OS_NAME.toLowerCase().startsWith(WIN) && StrUtil.isNotBlank(commandLine.getExecutable()) &&
                (commandLine.getExecutable().toLowerCase().endsWith(CMD)
                        || commandLine.getExecutable().toLowerCase().endsWith(BAT))) {
            charset = "GBK";
        }
        PumpStreamHandler streamHandler = new PumpStreamHandler(new CollectingLogOutputStream(cmdCallBack, result, charset));
        exec.setStreamHandler(streamHandler);
        // exit code: 0=success, 1=error
        exec.execute(commandLine);
    }

    private static void sendErrorLog(ExecutorRequestHandler.CmdCallBack cmdCallBack, ExecutorResult result) {
        result.setExecuteStatus(ExecutorStatus.STATUS_DOING);
        cmdCallBack.success(result);
    }

    public static class CollectingLogOutputStream extends LogOutputStream {

        private ExecutorRequestHandler.CmdCallBack cmdCallBack;

        private ExecutorResult result;

        public CollectingLogOutputStream(ExecutorRequestHandler.CmdCallBack cmdCallBack, ExecutorResult result, String charset) {
            super(charset);
            this.cmdCallBack = cmdCallBack;
            this.result = result;
        }

        @Override
        protected void processLine(String line, int level) {
            log.debug(line);
            result.setExecuteStatus(ExecutorStatus.STATUS_DOING);
            result.setResult(line);
            cmdCallBack.success(result);
        }

    }

}

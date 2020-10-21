package com.app.test.client;

import cn.hutool.core.util.CharsetUtil;
import com.opencloud.agent.client.common.CommandUtil;
import com.opencloud.agent.executor.ExecutorResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.*;

import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CmdTest {
    public static void main(String[] args) {
        String cmd = "echo $(date)\n" +
                "while true\n" +
                "do \n" +
                "pwd\n" +
                "sleep 1\n" +
                "done\n" +
                "ping -c 10 127.0.0.1";
        cmd("/bin/sh", "-c", cmd);
//        runShell(cmd);
//        hutoolCmd("/bin/sh", "-c", cmd);
    }

    public static void cmd(String sh, String c, String cmd) {
        try {
            CommandLine commandline = CommandLine.parse(sh);
            commandline.addArgument(c);
            commandline.addArgument(cmd, false);
            DefaultExecutor exec = new DefaultExecutor();
            exec.setWatchdog(new ExecuteWatchdog(ExecuteWatchdog.INFINITE_TIMEOUT));
            exec.setExitValues(null);
            ExecutorResult executorResult = new ExecutorResult();
            executorResult.setExecuteId(11L);
            PumpStreamHandler streamHandler = new PumpStreamHandler(new CommandUtil.CollectingLogOutputStream(
                    System.out::println, executorResult, CharsetUtil.UTF_8));
            exec.setStreamHandler(streamHandler);
            new Thread(() -> {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                exec.getWatchdog().destroyProcess();
            }).start();

            exec.execute(commandline);

        } catch (Exception e) {
            log.error("ping task failed.", e);
        }
    }

    /**
     * 运行shell并获得结果，注意：如果sh中含有awk,一定要按new String[]{"/bin/sh","-c",shStr}写,才可以获得流
     *
     * @param shStr 需要执行的shell
     * @return
     */
    public static List<String> runShell(String shStr) {
        List<String> strList = new ArrayList<String>();
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", shStr}, null, null);
            InputStreamReader ir = new InputStreamReader(process.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String line;
//            process.waitFor();
            while ((line = input.readLine()) != null) {
                strList.add(line);
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strList;
    }

}

package com.opencloud.agent.client.executor;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.opencloud.agent.ExecutorStatus;
import com.opencloud.agent.client.common.CommandUtil;
import com.opencloud.agent.client.common.HttpClientUtils;
import com.opencloud.agent.client.common.Run;
import com.opencloud.agent.client.proxy.ProxyReflectFactory;
import com.opencloud.agent.client.proxy.RpcService;
import com.opencloud.agent.executor.ExecutorFromServer;
import com.opencloud.agent.executor.ExecutorResult;
import com.opencloud.agent.service.ExecutorCenterService;
import com.opencloud.config.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import static com.opencloud.agent.BasicConfigurationConstants.VERSION_1;

public class ExecutorRequestHandler extends Thread {

    private static Logger logger = LoggerFactory.getLogger(ExecutorRequestHandler.class);
    private int retryTime = 1;
    private ExecutorFromServer fromServerInfo;

    public ExecutorRequestHandler() {
    }

    public void setFromServerInfo(ExecutorFromServer fromServerInfo) {
        this.fromServerInfo = fromServerInfo;
    }

    @Override
    public void run() {
        boolean executor = fromServerInfo.isExecutor();
        ExecutorResult result = new ExecutorResult();
        result.setType(MessageType.MESSAGE.getType());
        result.setExecuteId(fromServerInfo.getExecuteId());
        result.setExecuteStatus(ExecutorStatus.STATUS_DOING);
        result.setProxyIp(fromServerInfo.getProxyIp());
        result.setAgentChannelId(fromServerInfo.getAgentChannelId());
        result.setProxyChannelId(fromServerInfo.getProxyChannelId());
        result.setResult("开始执行: " + fromServerInfo.getCommand());
        result.setCommand(fromServerInfo.getCommand());
        result.setClientId(fromServerInfo.getClientId());
        result.setExecuteTime(System.currentTimeMillis());
        result.setExecutor(executor);
        result.setBrowerIp(fromServerInfo.getBrowerIp());
        asyncSend(result);
        String args = fromServerInfo.getArgs();

        ExecutorResult exeResult = BeanUtil.toBean(result, ExecutorResult.class);
        CommandUtil.executeCommand(fromServerInfo.getCommand(),
                args,
                exeResult,
                executor, this::asyncSend);
        if (executor) {
            if (StrUtil.isBlank(args)) {
                logger.info("执行完命令:{} ", fromServerInfo.getCommand());
            } else {
                logger.info("执行完命令:{},{}", fromServerInfo.getCommand(), args);
            }
            result.setExecuteStatus(ExecutorStatus.STATUS_DOING);
            if (args == null) {
                result.setResult("执行完成: " + fromServerInfo.getCommand());
            } else {
                result.setResult("执行完成: " + fromServerInfo.getCommand() + " " + args);
            }
            asyncSend(result);
        }
        result.setExecuteStatus(ExecutorStatus.STATUS_FINISH);
        result.setResult("执行结束!");
        asyncSend(result);
    }

    private void asyncSend(ExecutorResult executorResult) {
        executorResult.setExecuteTime(System.currentTimeMillis());
        logger.info(executorResult.toString());
        RpcService client = ProxyReflectFactory.createAsyncService(ExecutorCenterService.class, VERSION_1);
        try {
            client.call(ExecutorCenterService.RESPONSE, executorResult);
        } catch (Exception e) {
            retryAsyncSend(executorResult);
        }
    }

    private void retryAsyncSend(ExecutorResult executorResult) {
        for (int i = 0; i < retryTime; i++) {
            RpcService client = ProxyReflectFactory.createAsyncService(ExecutorCenterService.class, VERSION_1);
            try {
                client.call(ExecutorCenterService.RESPONSE, executorResult);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }

    private void uploadFromClientToServer(String clientPath, String serverPath) {
        if (StrUtil.isNotBlank(clientPath)) {
            List<String> paths = FileUtil.listFileNames(clientPath);
            for (String path : paths) {
                List<String> targetPath = this.getTargetPath(path, clientPath, serverPath);
                HttpClientUtils.upload(path, targetPath, "http://" + Run.serverHost + ":" + Run.serverPort + "/upload");
            }
        }
    }

    private List<String> getTargetPath(String path, String clientRootPath, String serverRootPath) {
        List<String> list = new ArrayList<>();
        if (StrUtil.isNotBlank(serverRootPath)) {
            File serverRootFile = new File(serverRootPath);
            list = Arrays.stream(serverRootFile.getPath().split(Matcher.quoteReplacement(File.separator))).filter(StrUtil::isNotBlank).collect(Collectors.toList());
        }
        File root = new File(clientRootPath);
        if (root.isFile()) {
            return list;
        }
        clientRootPath = root.getAbsolutePath();
        path = path.substring(0, path.lastIndexOf(File.separator));
        path = path.substring(clientRootPath.length());
        if (StrUtil.isBlank(path) || path.length() == 1) {
            return list;
        }
        path = path.substring(1);
        list.addAll(Arrays.asList(path.split(Matcher.quoteReplacement(File.separator))));
        return list;
    }

    private void downloadFormServerToClient(String clientRootPath, String serverRootPath) {
        String res = HttpClientUtils.get("http://" + Run.serverHost + ":" + Run.serverPort + "/all/files?basePath=" + serverRootPath);
        JSONObject json = JSONUtil.parseObj(res);
        JSONArray paths = json.getJSONArray("files");
        int isFile = json.getInt("isFile");
        for (Object path : paths) {
            File file = new File(path.toString());
            String sp = "";
            if (isFile == 0) {
                File srp = new File(serverRootPath);
                sp = srp.getPath();
            }
            HttpClientUtils.downLoad("http://" + Run.serverHost + ":" + Run.serverPort + "/download", sp, file.getPath(), clientRootPath);
        }
    }

    public void setRetryTime(Integer retryTime) {
        this.retryTime = retryTime;
    }

    public interface CmdCallBack {
        void success(ExecutorResult info);
    }
}

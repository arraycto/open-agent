package com.opencloud.agent.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.opencloud.agent.ExecutorStatus;
import com.opencloud.agent.Result;
import com.opencloud.agent.executor.ExecutorBrowerForm;
import com.opencloud.agent.message.IpsResultInfo;
import com.opencloud.agent.redis.RedisUtils;
import com.opencloud.agent.service.WebService;
import com.opencloud.agent.utils.FilesUtil;
import com.opencloud.agent.utils.RequestIpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.*;
import java.util.stream.Collectors;

import static com.opencloud.agent.BasicConfigurationConstants.INIT_NAME_PREFIX_PATTERN;

@Slf4j
@RequestMapping
@RestController
public class WebController {

    @Value("${workspace.dir}")
    private String workspaceDir;

    @Autowired
    private WebService webService;

    @Autowired
    private RedisUtils redisUtils;

    @GetMapping(value = "/cid")
    public Result index(HttpServletRequest request) {
        log.debug(RequestIpUtils.getIpAddr(request));
        Set<String> roots = redisUtils.keys(INIT_NAME_PREFIX_PATTERN);
        List<String> ipList = new ArrayList<>(roots);
        List<IpsResultInfo> result = new ArrayList<>();
        for (int i = 0; i < ipList.size(); i++) {
            result.add(IpsResultInfo.builder()
                    .id(i)
                    .label(ipList.get(i))
                    .build());
        }
        return Result.ok(result);
    }

    @PostMapping("/push")
    public Result push(HttpServletRequest request, @RequestBody ExecutorBrowerForm browerForm) {
        try {
            if (CollectionUtil.isEmpty(browerForm.getClientIds())
                    || StringUtils.isBlank(browerForm.getCommand())
                    || browerForm.getSessionId() == null) {
                return Result.error("参数不全");
            }
            webService.executor(browerForm, RequestIpUtils.getIpAddr(request));
            SerializeConfig config = new SerializeConfig();
            config.configEnumAsJavaBean(ExecutorStatus.class);
            String s = JSON.toJSONString(browerForm, config);
            return Result.ok(JSON.parseObject(s));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/stop")
    public Result stop(HttpServletRequest request, @RequestBody ExecutorBrowerForm browerForm) {
        try {
            if (CollectionUtil.isEmpty(browerForm.getClientIds())
                    || browerForm.getExecuteId() == null
                    || browerForm.getSessionId() == null) {
                return Result.error("参数不全");
            }
            webService.stop(browerForm, RequestIpUtils.getIpAddr(request));
            SerializeConfig config = new SerializeConfig();
            config.configEnumAsJavaBean(ExecutorStatus.class);
            String s = JSON.toJSONString(browerForm, config);
            return Result.ok(JSON.parseObject(s));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Result upload(@RequestParam("fn") String fn, @RequestParam("file") MultipartFile[] files) throws IOException {
        if (files.length == 0) {
            return Result.error();
        }
        String subDirs = null;
        if (StringUtils.isNotBlank(fn)) {
            List<String> dirs;
            try {
                dirs = JSON.parseArray(fn, String.class);
                if (dirs.size() > 0) {
                    subDirs = String.join(File.separator, dirs);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (MultipartFile file : files) {
            if (file != null) {
                // 取得当前上传文件的文件名称
                String fileName = file.getOriginalFilename();
                // 如果名称不为"",说明该文件存在，否则说明该文件不存在
                if (StringUtils.isNotBlank(fileName)) {
                    String filePath;
                    if (StringUtils.isNotBlank(subDirs)) {
                        filePath = workspaceDir + File.separator + subDirs + File.separator + fileName;
                    } else {
                        filePath = workspaceDir + File.separator + fileName;
                    }
                    File localFile = new File(filePath);
                    if (!localFile.getParentFile().exists()) {
                        localFile.getParentFile().mkdirs();
                    }
                    file.transferTo(localFile);
                }
            }
        }
        return Result.ok();
    }

    @RequestMapping(value = "/all/files")
    public Map<String, Object> allFiles(String basePath) {
        String path = workspaceDir + File.separator + basePath;
        File file = new File(path);
        Map<String, Object> map = new HashMap<>();
        map.put("isFile", 0);
        List<String> files = new ArrayList<>();
        map.put("files", files);
        if (!file.exists()) {
            return map;
        }
        if (file.isFile()) {
            files.add(basePath);
            map.put("isFile", 1);
            return map;
        }
        FilesUtil.getAllFilePaths(files, path);
        files = files.stream().map(t -> {
            File f = new File(t);
            return f.getAbsolutePath().substring(file.getAbsolutePath().length() + 1);
        }).collect(Collectors.toList());
        map.put("files", files);
        return map;
    }

    @RequestMapping(value = "/download")
    public ResponseEntity<byte[]> download(HttpServletRequest request) throws Exception {
        String fn = URLDecoder.decode(request.getHeader("fn"), "UTF-8");
        File f = new File(fn);
        File wd = new File(workspaceDir);
        String filePath = wd.getPath() + File.separator + f.getPath();
        File file = new File(filePath);
        if (file.isDirectory()) {
            return null;
        }
        if (!file.exists()) {
            return null;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", file.getName());
        return new ResponseEntity<>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
    }

}

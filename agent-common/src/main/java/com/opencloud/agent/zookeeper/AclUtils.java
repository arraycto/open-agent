package com.opencloud.agent.zookeeper;

import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.security.NoSuchAlgorithmException;

public class AclUtils {

    public static String getDigestUserPwd(String id) {
        // 加密明文密码
        try {
            return DigestAuthenticationProvider.generateDigest(id);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return id;
    }
}
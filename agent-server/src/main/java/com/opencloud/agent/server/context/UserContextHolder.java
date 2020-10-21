package com.opencloud.agent.server.context;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Optional;

/**
 * 用户上下文
 */
public class UserContextHolder {
    public static final String CHANNEL_ID = "channelID";

    private ThreadLocal<Map<String, Object>> threadLocal;

    private UserContextHolder() {
        this.threadLocal = new ThreadLocal<>();
    }

    /**
     * 创建实例
     */
    public static UserContextHolder getInstance() {
        return SingletonHolder.S_INSTANCE;
    }

    /**
     * 获取上下文中的信息
     */
    public Map<String, Object> getContext() {
        return Optional.ofNullable(threadLocal.get()).orElse(Maps.newHashMap());
    }

    /**
     * 用户上下文中放入信息
     */
    public void setContext(Map<String, Object> map) {
        threadLocal.set(map);
    }

    /**
     * 清空上下文
     */
    public void clear() {
        threadLocal.remove();
    }

    public void put(String key, String value) {
        Map<String, Object> context = threadLocal.get();
        if (CollectionUtil.isEmpty(context)) {
            context = Maps.newHashMap();
            setContext(context);
        }
        context.put(key, value);
    }

    public String getChannelId() {
        Object o = getContext().get(CHANNEL_ID);
        if (o == null) {
            return null;
        }
        return (String) o;
    }

    /**
     * 静态内部类单例模式
     * 单例初使化
     */
    private static class SingletonHolder {
        private static final UserContextHolder S_INSTANCE = new UserContextHolder();
    }

}

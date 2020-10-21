package com.opencloud.agent.server;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.opencloud.agent.lifecycle.LifecycleAware;
import com.opencloud.agent.lifecycle.LifecycleState;
import com.opencloud.agent.util.JsonUtil;
import com.opencloud.config.Configurable;
import com.opencloud.config.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;

import static com.opencloud.agent.BasicConfigurationConstants.AGENTN_NAME_REDIS_KEY_ALL;

public class RedisSource implements LifecycleAware, Configurable {

    /**
     * redis过期时间,以秒为单位
     */
    // 10秒
    public final static int EXPIRE_10_SECOND = 10;
    // 一小时
    public final static int EXPIRE_HOUR = 60 * 60;
    // 一天
    public final static int EXPIRE_DAY = 60 * 60 * 24;
    // 一个月
    public final static int EXPIRE_MONTH = 60 * 60 * 24 * 30;
    private static final Logger logger = LoggerFactory.getLogger(RedisSource.class);
    private static JedisPool pool = null;
    private LifecycleState lifecycleState;
    private String host;
    private Integer port;
    private String password;
    private Integer maxTotal;
    private Integer maxIdle;
    private Long maxWaitMillis;
    private Boolean testOnBorrow;
    private Boolean flush;

    private static Jedis getJedis() {
        logger.info("Redis poll active {},idle {},waiters {}",
                pool.getNumActive(), pool.getNumIdle(), pool.getNumWaiters());
        return pool.getResource();
    }

    /**
     * 获取指定key的值,如果key不存在返回null，如果该Key存储的不是字符串，会抛出一个错误
     */
    public synchronized static String get(String key) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = getJedis();
            if (jedis == null) {
                logger.error("Jedis is null");
                return result;
            }
            result = jedis.get(key);
        } catch (Exception e) {
            logger.error("获取数据异常:", e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    public synchronized static <T> T get(String key, Class<T> clazz) {
        return fromJson(get(key), clazz);
    }

    /**
     * JSON数据，转成Object
     */
    private static <T> T fromJson(String json, Class<T> clazz) {
        if (json == null) {
            return null;
        }
        return JsonUtil.jsonToObject(json, clazz);
    }

    /**
     * 设置key的值为value
     */
    public synchronized static String set(String key, String value) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = getJedis();
            if (jedis == null) {
                logger.error("Jedis is null");
                return result;
            }
            result = jedis.set(key, value);
        } catch (Exception e) {
            logger.error("获取数据异常:", e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    public synchronized static String setExpireDay(String key, int expire, String value) {
        Jedis jedis = null;
        String result = null;
        if (StrUtil.isBlank(key)) {
            return null;
        }
        try {
            jedis = getJedis();
            if (jedis == null) {
                logger.error("Jedis is null");
                return result;
            }
            result = jedis.setex(key, expire, value);
        } catch (Exception e) {
            logger.error("获取数据异常:", e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 删除指定的key,也可以传入一个包含key的数组
     *
     * @param keys
     * @return
     */
    public synchronized static Long del(String... keys) {
        if (keys == null || keys.length == 0) {
            return 0L;
        }
        Jedis jedis = null;
        Long result = 0L;
        try {
            jedis = getJedis();
            if (jedis == null) {
                logger.error("Jedis is null");
                return result;
            }
            result = jedis.del(keys);
        } catch (Exception e) {
            logger.error("获取数据异常:", e);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 释放jedis资源到连接池
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            pool.returnResource(jedis);
        }
    }

    @Override
    public synchronized void start() {
        logger.info("Redis client start");
        lifecycleState = LifecycleState.START;
        if (pool == null) {
            synchronized (this) {
                if (pool == null) {
                    JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
                    if (maxTotal != null) {
                        jedisPoolConfig.setMaxTotal(maxTotal);
                    }
                    if (maxIdle != null) {
                        jedisPoolConfig.setMaxIdle(maxIdle);
                    }
                    if (maxWaitMillis != null) {
                        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
                    }
                    if (testOnBorrow != null) {
                        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
                    }
                    if (password != null && !"".equals(password)) {
                        // redis 设置了密码
                        pool = new JedisPool(jedisPoolConfig, host, port, 10000, password);
                    } else {
                        // redis 未设置密码
                        pool = new JedisPool(jedisPoolConfig, host, port, 10000);
                    }
                }
            }
        }
        if (flush) {
            Jedis jedis = getJedis();
            if (jedis == null) {
                return;
            }
            Set<String> keys = jedis.keys(AGENTN_NAME_REDIS_KEY_ALL);
            if (CollectionUtil.isNotEmpty(keys)) {
                jedis.del(keys.toArray(new String[0]));
            }
            logger.warn("Delete open agent channel keys, start flush redis keys: {}", keys);
        }
    }

    @Override
    public synchronized void stop() {
        logger.info("Redis client stop");
        lifecycleState = LifecycleState.STOP;
        pool.destroy();
    }

    @Override
    public synchronized LifecycleState getLifecycleState() {
        return lifecycleState;
    }

    @Override
    public synchronized void configure(Context context) {
        this.host = context.getString("redis.host", "127.0.0.1");
        this.port = context.getInteger("redis.port", 6379);
        this.password = context.getString("redis.password", "");
        this.maxTotal = context.getInteger("redis.maxTotal", 100);
        this.maxIdle = context.getInteger("redis.maxIdle", 10);
        this.maxWaitMillis = context.getLong("redis.maxWaitMillis", 10 * 1000L);
        this.testOnBorrow = context.getBoolean("redis.testOnBorrow", true);
        this.flush = context.getBoolean("redis.flush", true);
    }


}

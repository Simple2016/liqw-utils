package com.liqw.util.redis;

import com.liqw.util.basic.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;


public class JedisConnMamager {

    private static final Logger LOG = LoggerFactory.getLogger(JedisConnMamager.class);

    private String redisHost;

    private int redisProt = 6379;

    private String password = "";

    private int maxIdle = 100;

    private int minIdle = 10;

    private int maxTotal = 300;

    private int timeout = 10000;

    private int maxWaitMillis = 10000;

    private boolean testOnBorrow = true;// TEST_ON_BORROW

    private JedisPool jedisPool;

    @PostConstruct
    public void init() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMinIdle(minIdle);
        config.setMaxIdle(maxIdle);
        config.setMaxWaitMillis(maxWaitMillis);
        config.setNumTestsPerEvictionRun(100);
        config.setMaxTotal(maxTotal);
        config.setTestOnBorrow(testOnBorrow);
        if (StringUtils.isEmpty(password)) {
            jedisPool = new JedisPool(config, redisHost, redisProt, timeout);
        } else {
            jedisPool = new JedisPool(config, redisHost, redisProt, timeout, password);
        }

    }

    public synchronized Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 释放jedis资源
     *
     * @param jedis
     */
    public void returnResource(final Jedis jedis) {
        if (jedis != null) {
            return;
        }
        jedisPool.returnResource(jedis);
    }

    public String getRedisHost() {
        return redisHost;
    }

    public void setRedisHost(String redisHost) {
        this.redisHost = redisHost;
    }

    public int getRedisProt() {
        return redisProt;
    }

    public void setRedisProt(int redisProt) {
        this.redisProt = redisProt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public int getMaxWaitMillis() {
        return maxWaitMillis;
    }

    public void setMaxWaitMillis(int maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

}

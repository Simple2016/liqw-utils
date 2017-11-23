package com.liqw.util.test;

import com.liqw.util.redis.JedisConnMamager;
import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * Created by liqw on 2017/11/23.
 */
public class TestRedis {

    @Test
    public void test() {
        JedisConnMamager jedisManager = new JedisConnMamager();
        jedisManager.setRedisHost("192.168.1.250");
        jedisManager.init();
        Jedis jedis = jedisManager.getJedis();
        jedis.incr("lqw");
        jedis.expire("lqw", 10);//设置key的生存时间
        System.err.println("ok");
    }

}


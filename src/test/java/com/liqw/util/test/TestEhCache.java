package com.liqw.util.test;

import com.liqw.util.cache.EhcacheUtil;
import org.junit.Test;

/**
 * Created by liqw on 2017/11/28.
 */
public class TestEhCache {
    @Test
    public void test1(){
        EhcacheUtil instance = EhcacheUtil.getInstance();
        String cacheName = "user_center_service";
        instance.put(cacheName, "a", "b");
        Object a = instance.get(cacheName, "a");
        System.out.println(a);
        instance.remove(cacheName,"a");
        System.out.println(instance.get(cacheName, "a"));
    }
}

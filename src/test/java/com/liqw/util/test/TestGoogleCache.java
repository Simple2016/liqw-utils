package com.liqw.util.test;

import com.liqw.util.cache.GoogleCacheUtil;
import org.junit.Test;

/**
 * Created by liqw on 2017/11/23.
 */
public class TestGoogleCache {
    @Test
    public void test() {

        GoogleCacheUtil<String, String> googleCacheUtil = new GoogleCacheUtil(new GoogleCacheUtil.Callback<String, String>() {
            @Override
            public String load(String key) {
                System.out.println("缓存为空，重新获取");
                //这里实现返回具体的值
                return "test";
            }
        });

        System.out.println(googleCacheUtil.get("abc"));
        System.out.println(googleCacheUtil.get("abc"));
        googleCacheUtil.refresh("abc");
        System.out.println(googleCacheUtil.get("abc"));

    }
}

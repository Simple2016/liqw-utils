package com.liqw.util.cache;

import com.google.common.cache.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by liqw on 2017/11/23.
 * google cache 只适合缓存某些业务的结果 ，不能缓存具体的值，可以内嵌到具体的代码
 */
public class GoogleCacheUtil<K, V> {
    private LoadingCache<K, V> cache;

    public GoogleCacheUtil(final Callback<K, V> callback) {
        //CacheBuilder的构造函数是私有的，只能通过其静态方法newBuilder()来获得CacheBuilder的实例
        cache = CacheBuilder.newBuilder()
                //设置并发级别为8，并发级别是指可以同时写缓存的线程数
                .concurrencyLevel(8)
                //设置写缓存后8秒钟过期
                .expireAfterWrite(7200, TimeUnit.SECONDS)
                //设置缓存容器的初始容量为10
                .initialCapacity(10)
                //设置缓存最大容量为100，超过100之后就会按照LRU最近虽少使用算法来移除缓存项
                .maximumSize(100)
                //设置要统计缓存的命中率
                .recordStats()
                //设置缓存的移除通知
                .removalListener(new RemovalListener<K, V>() {
                    @Override
                    public void onRemoval(RemovalNotification<K, V> notification) {
                        System.out.println(notification.getKey() + " was removed, cause is " + notification.getCause());
                    }
                })
                //build方法中可以指定CacheLoader，在缓存不存在时通过CacheLoader的实现自动加载缓存
                .build(
                        new CacheLoader<K, V>() {

                            @Override
                            public V load(K key) throws Exception {
                                return callback.load(key);
                            }
                        }
                );

    }

    public V get(K key) {
        try {
            return this.cache.get(key);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public void refresh(K key) {
        cache.refresh(key);
    }

    public interface Callback<K, V> {
        V load(K key);
    }
}

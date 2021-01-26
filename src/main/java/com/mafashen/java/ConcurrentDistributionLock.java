package com.mafashen.java;

import java.util.concurrent.TimeUnit;

/**
 * 分段分布式锁
 * @author mafashen
 * @since 2019-10-08.
 */
public class ConcurrentDistributionLock {

    private int segment;

    private int defaultExpireTime;

    private TimeUnit timeUnit;

    public boolean tryLock(String key){
        return true;
    }

    public boolean tryLock(String key, long expire){
        return true;
    }

    public boolean tryLock(String key, long expire, TimeUnit timeUnit){
        return true;
    }

    public boolean lock(String key){
        return true;
    }

    public boolean lock(String key, long expire){
        return true;
    }

    public boolean lock(String key, long expire, TimeUnit timeUnit){
        // 随机选择一个段,尝试加锁
        // 失败自动尝试下一个段
        // 重试几个段?
        return true;
    }

    private int hash(String key){
        return key.hashCode();
    }
}

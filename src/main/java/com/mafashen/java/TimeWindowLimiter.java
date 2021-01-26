package com.mafashen.java;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * 基于缓存思想的时间窗口限流
 * @author mafashen
 * created on 2019/6/23.
 */
public class TimeWindowLimiter {
	private final static int LIMIT = 100;

	private LoadingCache<Long, AtomicInteger> counter = CacheBuilder.newBuilder()
			.expireAfterWrite(2, TimeUnit.SECONDS)
			.build(new CacheLoader<Long, AtomicInteger>() {
		@Override
		public AtomicInteger load(Long key) throws Exception {
			return new AtomicInteger(0);
		}
	});

	public void acquire() throws ExecutionException {
		while (true){
			long currentSeconds = System.currentTimeMillis() / 1000;
			if (counter.get(currentSeconds).incrementAndGet() > LIMIT){
				System.out.println("Request Limit");
			}else{
				break;
			}
		}
	}
}

package com.mafashen.java;

import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 需要确定两点
 * 	1.线程安全性
 * 	2.吞吐量
 * @author mafashen
 * created on 2019/5/1.
 */
public class SyncWorker<V>{
	Logger logger = LoggerFactory.getLogger(SyncWorker.class);

	ConcurrentHashMap<Long, ConcurrentHashMap<Long, V>> prepareQueue = new ConcurrentHashMap<>(100);
	ConcurrentHashMap<Long, ConcurrentHashMap<Long, V>> readyQueue = new ConcurrentHashMap<>(100);
	ConcurrentLinkedQueue<Long> timingQueue = new ConcurrentLinkedQueue<>();
	ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(4);


	public SyncWorker() {
		scheduledExecutorService.scheduleWithFixedDelay(new TimeOutCheck(), 30, 30, TimeUnit.SECONDS);
		scheduledExecutorService.scheduleWithFixedDelay(new InnerWorker(), 0, 5, TimeUnit.SECONDS);
	}

	public void put(Long key, Long value, V v){
		//value已经在ready队列里,直接更新原来的值
		if (readyQueue.containsKey(key)){
			ConcurrentHashMap<Long, V> readyShopItemIds = readyQueue.get(key);
			if (readyShopItemIds.containsKey(value)){
				readyShopItemIds.put(value, v);
				return;
			}
		}
		if (!prepareQueue.containsKey(key)){
			prepareQueue.putIfAbsent(key, new ConcurrentHashMap<>());
			timingQueue.add(key);
		}
		prepareQueue.get(key).put(value, v);
		//到达指定长度,或者timeout
		ConcurrentHashMap<Long, V> exist = prepareQueue.get(key);
		if (exist.size() == 50){
			logger.warn("key : " + key + " size is equal 50 , remove from prepareQueue ");
			//加入ready前判断readyQueue中是否已经存在对应key,如果存在且sum size 大于50, 不能加入, 或者考虑直接消费, 但是不好做统一限流
			readyQueue.putIfAbsent(key, new ConcurrentHashMap<>());
			readyQueue.get(key).putAll(exist);
			prepareQueue.remove(key);
		}
	}

	//key 超时检查
	class TimeOutCheck implements Runnable{
		@Override
		public void run() {
			//将prepareQueue中加入时间超过 x 的元素从prepareQueue移除,并加入到readyQueue中
			//TODO 怎么保证尽可能快且少并发竞争的遍历所有元素,并计算超时(数据量特别大的时候)
			// 优先级队列 LRU类似, 每次选取最后面一批, 可能出现的情况,每次在快超时时, 又被使用,导致不在尾部,
			// 最坏情况,需要一直等到达到限定长度, 需要等待 Max * Timeout
			Iterator<Long> iterator = timingQueue.iterator();
			for (int i = 0; i < 100 && iterator.hasNext(); i++) {
				Long key = iterator.next();
				if (!prepareQueue.containsKey(key)){
					iterator.remove();
					continue;
				}
				ConcurrentHashMap<Long, V> remove = prepareQueue.get(key);
				ConcurrentHashMap<Long, V> exist = readyQueue.get(key);
				if (exist == null){
					readyQueue.put(key, remove);
					iterator.remove();
					prepareQueue.remove(key);
					logger.warn("key : " + key + " is timeout , remove from prepareQueue, size : " + remove.size());
				}else if (exist.size() + remove.size() < 50){
					readyQueue.get(key).putAll(remove);
					iterator.remove();
					prepareQueue.remove(key);
					logger.warn("key : " + key + " is timeout , remove from prepareQueue, size : " + remove.size());
				}else{
					//保留 填充到50
					logger.warn("key : " + key + " is timeout , but readyQueue exist key and sum large than 50, now size : " + remove.size());
				}
			}
		}
	}

	class InnerWorker implements Runnable{
		@Override
		public void run() {
			//定时从readyQueue取,搭配限流策略
			Iterator<Long> iterator = readyQueue.keySet().iterator();
			for (int i = 0; i < 100 && iterator.hasNext(); i++) {
				Long next = iterator.next();
				ConcurrentHashMap<Long, V> exist = readyQueue.get(next);
				if (exist.size() <= 50){
					logger.warn("consume key : " + next + " size : " + exist.size());
					readyQueue.remove(next);
				}else{
					Iterator<Long> innerIter = exist.keySet().iterator();
					for (int j = 0; j < 50 && innerIter.hasNext(); j++) {
						exist.remove(innerIter.next());
					}
					logger.warn("consume key : " + next + " size : " + exist.size() + " remove early 50, now size : " + exist.size());
				}
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		CountDownLatch latch = new CountDownLatch(100);
		Random random = new Random();
		SyncWorker syncWorker = new SyncWorker();
		Executor executor = Executors.newFixedThreadPool(20);
		for (int i = 0; i < 1000000; i++) {
			Thread.sleep(2);
			executor.execute(new Runnable() {
				@Override
				public void run() {
					syncWorker.put(Long.valueOf(random.nextInt(10000)),
							Long.valueOf(random.nextInt(10000000)), random.nextInt(100));
					latch.countDown();
				}
			});
		}
		latch.await();
	}
}

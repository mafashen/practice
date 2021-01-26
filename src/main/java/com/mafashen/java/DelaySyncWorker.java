package com.mafashen.java;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author mafashen
 * created on 2019/6/10.
 */
public class DelaySyncWorker {

	private DelayQueue<DelayWorker> delayQueue;
	private ConcurrentHashMap<Long, List<Long>> onlineMap;

	private void put(Long key, Long itemId){

	}


	static class DelayWorker implements Delayed{
		private long addTime;
		private long shopId;
		private Set<Long> itemIds;

		public DelayWorker(long addTime, long shopId) {
			this.addTime = addTime;
			this.shopId = shopId;
		}

		@Override
		public int compareTo(Delayed o) {
			if (o == this){
				return 0;
			}
			if (o instanceof DelayWorker){
				DelayWorker ot = (DelayWorker)o;
				return (int) (ot.getShopId() - getShopId());
			}
			return 0;
		}

		@Override
		public long getDelay(TimeUnit unit) {
			return System.currentTimeMillis() - addTime - 60 * 1000;
		}

		public long getShopId() {
			return shopId;
		}
	}

//	static class MyDelayQueue<DelayWorker extends Delayed> extends DelayQueue<DelayWorker>{
//		@Override
//		public boolean add(DelayWorker delayed) {
//			return super.add(delayed);
//		}
//	}
}

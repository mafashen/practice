package com.mafashen.invoke;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.collections4.CollectionUtils;

public class JdCatGet {
	static BlockingQueue blockingQueue = new ArrayBlockingQueue(1000);
	private static ExecutorService executor = new ThreadPoolExecutor(3, 6, 1 , TimeUnit.MINUTES , blockingQueue , new DamonThreadFactory());

	private static BlockingQueue<CategoryInfo> catQueue = new ArrayBlockingQueue<CategoryInfo>(3000);
	private static ConcurrentHashMap<Long , String> allCats = new ConcurrentHashMap();
	private static AtomicBoolean stopFlag = new AtomicBoolean(false);

	public static void main(String[] args) {
		//先获取一级类目
		catQueue.add(new CategoryInfo(0L));
		while (!stopFlag.get()){
			try {
				//获取顺序, 一级类目 -> 二级类目 -> 三级类目 , 二级类目获取完毕后,获取三级类目的时候,总数不在增长,此时可以停止
				System.out.println("allCats size is " + allCats.size());

				CategoryInfo cat = catQueue.poll(10 , TimeUnit.SECONDS);
				System.out.println("cat one from BlockQueue is " + cat);
				if (cat != null) {
					executor.execute(new GetCatTask(cat.getId()));
				}
				else {
					break;
				}

				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

		for (Long id : allCats.keySet()) {
			System.out.println(id  + " --- " + allCats.get(id));
		}
		System.out.println("main thread will died " + blockingQueue.size());
	}

	static class GetCatTask implements Runnable{
		long catId ;
		GetCatTask(long catId){
			this.catId = catId;
		}

		@Override
		public void run() {
			try {
				invokeToGetCat(catId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static void invokeToGetCat(long catId){
		try {
			Map<String, Object> param = new HashMap<>();
			param.put("id", catId);
			param.put("fields", Arrays.asList("ID", "PID", "CATEGORY_NAME", "CATEGORY_LEVEL", "CATEGORY_STATUS", "FULLPATH"));
			String response = JdApiInvoke.buildProtocolAndRequest("api/queryChildCategoriesForOP", param);

			List<CategoryInfo> cats = JdApiInvoke.parse(response);
			if (CollectionUtils.isNotEmpty(cats)) {
				for (CategoryInfo categoryInfo : cats) {
					catQueue.put(categoryInfo);
					allCats.put(categoryInfo.getId(), categoryInfo.getCategoryName());
				}
			}else{
				//TODO 如果某个类目刚好是一级或者二级类目,没有三级类目,gg了 不能基于假设编程
				stopFlag.set(true);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void takeFromQueueAndWriteToFile(){
		File file = new File("/Users/mafashen/Documents", "jdCat.txt");
		try (FileWriter fw = new FileWriter(file); BufferedWriter bw = new BufferedWriter(fw)){
			file.createNewFile();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static class DamonThreadFactory implements ThreadFactory{

		@Override
		public Thread newThread(Runnable r) {
			Thread thread = new Thread(r);
			thread.setDaemon(true);
			return thread;
		}
	}
}

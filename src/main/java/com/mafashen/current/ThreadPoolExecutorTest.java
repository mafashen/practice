package com.mafashen.current;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorTest {
	static BlockingQueue blockingQueue = new ArrayBlockingQueue(100);
	private static ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 6, 1 , TimeUnit.MILLISECONDS , blockingQueue );

	private static CountDownLatch countDownLatch = new CountDownLatch(20);

	public static void main(String[] args) throws InterruptedException {
		executor.allowCoreThreadTimeOut(true);
		for (int i = 0; i < 20; i++) {

			executor.execute(new Runnable() {

				@Override
				public void run() {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("thread will finish");
					countDownLatch.countDown();
				}
			});
		}

		countDownLatch.await();

		System.out.println("largest pool size : " + executor.getLargestPoolSize());
		System.out.println("active count : " + executor.getActiveCount());
		System.out.println("is shutdown : " + executor.isShutdown());
		System.out.println("is terminated : " + executor.isTerminated());
		System.out.println("main thread will died");
	}
}

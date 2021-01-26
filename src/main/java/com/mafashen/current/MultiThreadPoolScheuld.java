package com.mafashen.current;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @author mafashen
 * created on 2018/12/13.
 */
public class MultiThreadPoolScheuld {

	public static void main(String[] args) {

		Executor executor1 = new ThreadPoolExecutor(5, 50, 100, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), new MyThreadFactory("executor1"));
		Executor executor2 = new ThreadPoolExecutor(10, 50, 100, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new MyThreadFactory("executor2"));
		Executor executor3 = new ThreadPoolExecutor(20, 50, 100, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new MyThreadFactory("executor3"));

		for (int i = 0; i < 100000; i++) {
			executor1.execute(new InnerRunner());
			executor2.execute(new InnerRunner());
			executor3.execute(new InnerRunner());
		}
	}

	static class MyThreadFactory implements ThreadFactory{

		private String group;

		public MyThreadFactory(String group) {
			this.group = group;
		}

		@Override
		public Thread newThread(Runnable r) {
			Thread thread = new Thread(r);
			thread.setName(group + " - " + System.currentTimeMillis());
			return thread;
		}
	}

	static class InnerRunner implements Runnable{

		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName());
			try {
				Thread.sleep(System.currentTimeMillis() % 100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

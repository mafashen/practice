package com.mafashen.java;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试堆内存大小对线程数量的影响
 * @author mafashen
 * created on 2019/6/13.
 */
public class ThreadAndHeapSizeTest {

	//-Xmx1g : 4071
	public static void main(String[] args) {
		AtomicInteger count = new AtomicInteger(0);
		while(true){
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					System.out.println(count.getAndIncrement());
					try {
						Thread.sleep(10*60*1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
			t.start();
		}
	}
}

package com.mafashen.jvm.gc;

import java.util.concurrent.CountDownLatch;

/**
 * @author mafashen
 * created on 2018/11/20.
 */
public class SafePointTest {

	static double sum = 0;
	static long start ;
	static CountDownLatch latch;

	private static void foo(){
		for (int i = 0; i < 0x77777777; i++) {
			sum += Math.sqrt(i);
		}
		latch.countDown();
	}

	public static void bar() {
		for (int i = 0; i < 50_000_000; i++) {
			new Object().hashCode();
		}
		latch.countDown();
	}

	// -XX:+PrintGC -XX:+PrintGCApplicationStoppedTime -XX:+PrintSafepointStatistics -XX:+UseCountedLoopSafepoints
	public static void main(String[] args) throws InterruptedException {
		latch = new CountDownLatch(1);
		start = System.currentTimeMillis();

//		new Thread(SafePointTest::foo).start();	//10926 ms
		new Thread(SafePointTest::bar).start(); //takes : 3579 ms

		latch.await();	//takes : 9956 ms
		System.out.println("takes : " + (System.currentTimeMillis() - start) + " ms");
	}
}

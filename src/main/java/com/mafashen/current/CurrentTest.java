package com.mafashen.current;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import org.junit.Test;

public class CurrentTest {

	public static void main(String[] args) {
		ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
		ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
		for (ThreadInfo threadInfo : threadInfos) {
			System.out.println("[" + threadInfo.getThreadId() + "]  " + threadInfo.getThreadName());
		}
	}

	@Test
	public void testStartThreadManyTimes(){
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("running...");
			}
		});

		t.start();
		System.out.println(t.getState());
//		t.start();
		System.out.println(t.getState());
		t.start();
		System.out.println(t.getState());
	}

	volatile int sum ;
	CyclicBarrier barrier = new CyclicBarrier(1001);

	@Test
	public void testVolatile() throws BrokenBarrierException, InterruptedException {
		for (int i = 0; i < 1000; i++) {
			new Thread(()->{
				try {
					barrier.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}
				setCount();
			}).start();
		}
		barrier.await();
		System.out.println(sum);
	}

	private void setCount() {
//		synchronized (this){
			for (int j = 0; j < 1000; j++) {
				sum ++;
			}
//		}
	}

	@Test
	public void test(){
		System.out.println((-1 << (Integer.SIZE - 3)));
		System.out.println(Integer.toHexString((-1 << (Integer.SIZE - 3))));
	}


}

package com.mafashen.current;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

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
}

package com.mafashen.netty.procotol.pro.util;

import java.util.concurrent.atomic.AtomicInteger;

public final class Counter {

	private static final int MAX_FAILED_TIMES = 10;
	private static AtomicInteger failed = new AtomicInteger(0);

	public static void inc(){
		if (failed.get() == MAX_FAILED_TIMES){
			throw new RuntimeException("Heart Beat Failed " + MAX_FAILED_TIMES + " Times ");
		}
		failed.getAndIncrement();
	}

	public static void clear(){
		failed.set(0);
	}
}

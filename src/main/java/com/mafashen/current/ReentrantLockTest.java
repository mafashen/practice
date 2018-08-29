package com.mafashen.current;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;

public class ReentrantLockTest {

	private static ReentrantLock2 failLock = new ReentrantLock2(true);
	private static ReentrantLock2 unFailLock = new ReentrantLock2(false);

	private CyclicBarrier cyclicBarrier = new CyclicBarrier(10);
	private CountDownLatch countDownLatch = new CountDownLatch(10);

	private static class ReentrantLock2 extends ReentrantLock {

		public ReentrantLock2(boolean fair) {
			super(fair);
		}

		public Collection<Thread> getQueuedThreads() {
			List<Thread> queuedThreads = new ArrayList<>(super.getQueuedThreads());
			Collections.reverse(queuedThreads);
			return queuedThreads;
		}
	}

	private static class Job extends Thread{
		private ReentrantLock2 lock;

		private Job(ReentrantLock2 lock) {
			this.lock = lock;
		}

		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName() + "running");
			while(true){
				lock.lock();
				try {
					System.out.println("Locked by [" + Thread.currentThread().getName() + "] , Waiting by " + lock.getQueuedThreads() );
					System.out.println("Locked by [" + Thread.currentThread().getName() + "] , Waiting by " + lock.getQueuedThreads() );
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}
			}
		}
	}

	private void testInternal(ReentrantLock2 lock){
		for (int i = 0; i < 5; i++) {
			Job job = new Job(lock);
			job.setDaemon(true);
			job.start();
		}
	}

	@Test
	public void testFailLock(){
		testInternal(failLock);
	}

	@Test
	public void testUnFailLock(){
		testInternal(unFailLock);
	}

	public static void main(String[] args) {
		new ReentrantLockTest().testInternal(failLock);
	}

	@Test
	public void testReentrant() throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			new RecursionJob().start();
		}
		countDownLatch.await();
	}

	class RecursionJob extends Thread{

		@Override
		public void run() {
			recursion(10);
			countDownLatch.countDown();
		}
	}

	public void recursion(int i){
		System.out.println(Thread.currentThread().getName() + " come in " + i);
		unFailLock.lock();
		try {
			System.out.println(Thread.currentThread().getName() + " get the lock " + i);
			if (i-- > 0){
				recursion(i);
			}
		} finally {
			unFailLock.unlock();
			System.out.println(Thread.currentThread().getName() + " release the lock " + i);
		}
	}
}

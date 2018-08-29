package com.mafashen.current;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class TwinsLock implements Lock {

	private final Sync sync = new Sync(2);

	@Override
	public void lock() {
		sync.acquireShared(1);
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {

	}

	@Override
	public boolean tryLock() {
		return false;
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return false;
	}

	@Override
	public void unlock() {
		sync.releaseShared(1);
	}

	@Override
	public Condition newCondition() {
		return null;
	}

	public int getState(){
		return sync.get4State();
	}

	private final static class Sync extends AbstractQueuedSynchronizer {

		public Sync(int count) {
			setState(count);
		}

		@Override
		protected int tryAcquireShared(int reduceCount) {
			for(;;){
				int current = getState();
//				System.out.println(Thread.currentThread().getName() + "  current  " + current);
				int newCount = current - reduceCount;
//				System.out.println(Thread.currentThread().getName() + "  newCount  " + newCount);
				if (newCount < 0 || compareAndSetState2(current , newCount)){
//					System.out.println("get lock " + Thread.currentThread().getName());
					return newCount;
				}
			}
		}

		@Override
		protected boolean tryReleaseShared(int returnCount) {
			for(;;){
				int current = getState();
//				System.out.println("release ----" + Thread.currentThread().getName() + "  current  " + current);
				int newCount = current + returnCount;
//				System.out.println("release ----" + Thread.currentThread().getName() + "  newCount  " + newCount);
				if (compareAndSetState2(current , newCount)){
//					System.out.println("release lock " + Thread.currentThread().getName());
					return true;
				}
			}
		}

		public int get4State(){
			return getState();
		}

		private boolean compareAndSetState2(int expireState , int newState){
			if (compareAndSetState(expireState , newState)){
//				System.out.println("compareAndSet success " + Thread.currentThread().getName());
				return true;
			}
			return false;
		}
	}

	public static class TwinsLockTest {

		public static void main(String[] args) {
			final TwinsLock lock = new TwinsLock();

			class Runner extends Thread {


				@Override
				public void run() {
					while (true) {
						lock.lock();
						try {
							Thread.sleep(1000);
							System.out.println(Thread.currentThread().getName() + " ----- running ----- " + lock.getState());
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}finally {
							lock.unlock();
						}
					}
				}
			}

			for (int i = 0; i < 10; i++) {
				Runner runner = new Runner();
				runner.setDaemon(true);
				runner.start();
			}

			for (int i = 0; i < 10; i++) {
				try {
					Thread.sleep(1000);
					System.out.println();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

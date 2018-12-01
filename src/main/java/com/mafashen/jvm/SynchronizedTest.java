package com.mafashen.jvm;

/**
 * @author mafashen
 * created on 2018/11/22.
 */
// Run with -XX:+UnlockDiagnosicVMOptions -XX:+PrintBiasedLockingStatistics -XX:TieredStopAtLevel=1
public class SynchronizedTest {
	static Lock lock = new Lock();
	static int counter = 0;
	public static void foo() {
		synchronized (lock) {
			counter++;
		}
	}
	public static void main(String[] args) throws InterruptedException {
		 lock.hashCode(); // Step 2
		 System.identityHashCode(lock); // Step 4
		for (int i = 0; i < 1_000_000; i++) {
			foo();
		}
	}
	static class Lock {
		 @Override public int hashCode() {
		 	return 0;
		 } // Step 3
	}

/*
开启偏向锁
# total entries: 0
# biased lock entries: 0
# anonymously biased lock entries: 0
# rebiased lock entries: 0
# revoked lock entries: 0
# fast path lock entries: 2089636
# slow path lock entries: 2

关闭偏向锁
# total entries: 0
# biased lock entries: 0
# anonymously biased lock entries: 0
# rebiased lock entries: 0
# revoked lock entries: 0
# fast path lock entries: 2074612
# slow path lock entries: 2

step2
# total entries: 0
# biased lock entries: 0
# anonymously biased lock entries: 0
# rebiased lock entries: 0
# revoked lock entries: 0
# fast path lock entries: 2122537
# slow path lock entries: 2
 */
}

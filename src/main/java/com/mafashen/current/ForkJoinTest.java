package com.mafashen.current;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTest {

	private static final int size = 999999;

	static class CountTask extends RecursiveTask<Integer>{
		static final int THRESHOLD = 2000;

		private int start ;
		private int end ;

		public CountTask(int start , int end){
			this.end = end;
			this.start = start;
		}

		@Override
		protected Integer compute() {
			int sum = 0;
			boolean canCompute = (this.end - this.start ) > THRESHOLD;
			if (canCompute){
				for (int i = start; i <= end; i++) {
					sum = add(i , sum);
				}
			}else {
				int middle = (start + end) / 2;
				CountTask leftTask = new CountTask(start, middle);
				CountTask rightTask = new CountTask(middle+1, end);

				leftTask.fork();
				rightTask.fork();

				Integer leftRet = leftTask.join();
				Integer rightRet = rightTask.join();

				sum = leftRet + rightRet;
			}
			return sum;
		}
	}

	static int add (int a , int b){
		try {
			Thread.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return a + b;
	}

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();

		ForkJoinPool forkJoinPool = new ForkJoinPool();
		CountTask countTask = new CountTask(1, size);
		ForkJoinTask<Integer> sum = forkJoinPool.submit(countTask);

		try {
			System.out.println(sum.get());

			System.out.println("takes " + (System.currentTimeMillis() - startTime) + " ms");
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (countTask.isCompletedAbnormally()){
			System.out.println(countTask.getException());
		}

		startTime = System.currentTimeMillis();

		int count = 0;
		for (int i = 0; i <= size; i++) {
			count = add(i , count);
		}

		System.out.println("takes " + (System.currentTimeMillis() - startTime) + " ms");
	}
}

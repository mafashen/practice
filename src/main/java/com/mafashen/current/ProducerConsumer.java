package com.mafashen.current;

import java.util.ArrayList;
import java.util.List;

public class ProducerConsumer {

	static class MyStack {
		private List list = new ArrayList();

		public synchronized void push(){
			try {
				while (list.size() == 1){
					System.out.println(Thread.currentThread().getName() + " push wait...");
					this.wait();
				}
				list.add("anyString=" + Math.random());

				this.notifyAll();

				System.out.println("push = " + list.size());
			} catch (Exception e) {
				System.out.println(Thread.currentThread().getName() + e.getMessage());
			}
		}

		public synchronized void pop(){
			String value = "";

			try{
				while (list.size() == 0){
					System.out.println(Thread.currentThread().getName() + "  pop操作中的:" + " 线程wait");
					this.wait();
				}
				value = "" + list.get(0);

				list.remove(0);
				this.notifyAll();
				System.out.println(Thread.currentThread().getName() + "  pop = " + list.size());
			} catch (Exception e) {
				System.out.println(Thread.currentThread().getName() + e.getMessage());
			}
		}
	}

	static class Producer extends Thread{
		private MyStack stack;

		public Producer(MyStack stack) {
			this.stack = stack;
		}

		@Override
		public void run() {
			for ( ; ; )
				stack.push();
		}
	}

	static class Consumer extends Thread{
		private MyStack stack;

		public Consumer(MyStack stack){
			this.stack = stack;
		}

		@Override
		public void run() {
			for (; ; )
				stack.pop();
		}
	}

	public static void main(String[] args) {
		MyStack stack = new MyStack();

		Producer producer = new Producer(stack);
		Consumer consumer1 = new Consumer(stack);
		Consumer consumer2 = new Consumer(stack);
		Consumer consumer3 = new Consumer(stack);
		Consumer consumer4 = new Consumer(stack);

		producer.start();
		consumer1.start();
		consumer2.start();
		consumer3.start();
		consumer4.start();
	}
}

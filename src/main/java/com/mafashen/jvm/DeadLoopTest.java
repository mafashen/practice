package com.mafashen.jvm;

public class DeadLoopTest {

	static {
		//不加入if,编译器将提示 Initializer does complete normally
		if (true) {
			System.out.println(Thread.currentThread() + "init DeadLoopTest");
			while(true) {
				
			}
		}
	}
	
	public static void main(String[] args) {
		
		Runnable script = new Runnable() {
			
			@Override
			public void run() {
				System.out.println(Thread.currentThread() + "start ");
				DeadLoopTest deadLoopTest = new DeadLoopTest();
				System.out.println(Thread.currentThread() + "run over");
			}
		};
		
		Thread t1 = new Thread(script);
		Thread t2 = new Thread(script);
		t1.start();
		t2.start();
	}
}

package com.mafashen.jvm;

public class StackVarTest {

	static int c = 0;

	public static void main(String[] args) {
//		for (int i = 0; i < 5; i++) {
//			new Thread(new Runnable() {
//				@Override
//				public void run() {
//					for (int i = 0; i < 50000; i++) {
//						c++;
//					}
//				}
//			}).start();
//		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 50000; i++) {
					c++;
				}
			}
		}).start();
		System.out.println(c);
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 50000; i++) {
					c++;
				}
			}
		}).start();
		System.out.println(c);
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 50000; i++) {
					c++;
				}
			}
		}).start();
		System.out.println(c);
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 50000; i++) {
					c++;
				}
			}
		}).start();
		System.out.println(c);
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 50000; i++) {
					c++;
				}
			}
		}).start();
		System.out.println(c);
	}
}

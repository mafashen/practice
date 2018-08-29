package com.mafashen.jvm;

import java.util.HashMap;
import javax.servlet.jsp.JspFactory;

public class StopTheWorldTest {

	public static class Producer extends Thread{
		HashMap map = new HashMap();

		@Override
		public void run() {
			try {
				while (true){
					if (map.size() * 512 / 1024 / 1024 >= 900){
						map.clear();
						System.out.println("clean map ");
					}
					byte[] bytes = null;
					for (int i = 0; i < 100; i++) {
						bytes = new byte[512];
						map.put(System.currentTimeMillis() , bytes);
					}
					Thread.sleep(1);
				}
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static class Customer extends Thread{
		public static final long startTime = System.currentTimeMillis();

		@Override
		public void run() {
			try {
				while (true){
					long t = System.currentTimeMillis() - startTime;
					System.out.println(t / 1000 + "." + t % 1000);
					Thread.sleep(100);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		Producer producer = new Producer();
		Customer customer = new Customer();

		producer.start();
		customer.start();
	}
}

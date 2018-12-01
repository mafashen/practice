package com.mafashen.java;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

import org.junit.Test;

public class CallbackTest {

	interface Callback{
		void callback();
	}

	public static void main(String[] args) {
		OuterRunner runner = new OuterRunner();
		new Thread(runner).start();
		while (true) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Setter
	@Getter
	static class Context {
		Callback callback;
	}

	static class OuterRunner implements Runnable{
		@Override
		public void run() {
			InnerRunner runner = new InnerRunner();
			new Thread(runner).start();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			runner.ctx.getCallback().callback();
			while (true){}
		}
	}

	static class InnerRunner implements Runnable{
		Context ctx ;

		public InnerRunner() {
			ctx = new Context();
		}

		@Override
		public void run() {

			ctx.setCallback(new Callback() {
				@Override
				public void callback() {
					System.out.println("callback");
				}
			});

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
	}

	@Test
	public void test(){
		Object[] args = null;
		List<Object> list = Arrays.asList(args);
		list.add(1);
		System.out.println(list);
	}

}

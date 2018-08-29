package com.mafashen.jvm;

public class LongFinalize {

	public static class LF{
		private byte[] content = new byte[512];

		@Override
		protected void finalize() throws Throwable {
			try {
				System.out.println(Thread.currentThread().getId());
				Thread.sleep(1000);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();

		for (int i = 0; i < 50000; i++) {
			LF lf = new LF();
		}

		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}
}

package com.mafashen.jvm;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/*
虚引用：最弱的，随时可能被回收，get方法获取强引用总是失败，必须和引用队列一起使用，作用在与跟踪垃圾回收过程。
		当垃圾回收器准备回收一个对象时，如果发现它还有虚引用，就会在回收对象后，将这个虚引用加入引用队列，
		以通知应用程序对象的回收情况。可以追踪对象的回收时间，可以将一些资源释放操作放在虚引用中执行和记录。
 */
public class PhantomTraceReliveObj {

	private static PhantomTraceReliveObj obj ;

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		System.out.println("CanReliveObj finalize method called ");
		obj = this;
	}

	static ReferenceQueue<PhantomTraceReliveObj> phantomQueue = null;

	public static class CheckPhantomReferenceQueue extends Thread{

		@Override
		public void run() {
			while (true){
				if (phantomQueue != null){
					PhantomReference<PhantomTraceReliveObj> ref = null;
					try {
						ref = (PhantomReference<PhantomTraceReliveObj>) phantomQueue.remove();
						if (ref != null){
							System.out.println("PhantomTraceReliveObj is died ");
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Thread checkThread = new CheckPhantomReferenceQueue();
		checkThread.setDaemon(true);
		checkThread.start();

		obj = new PhantomTraceReliveObj();
		phantomQueue = new ReferenceQueue<>();
		PhantomReference phantomReference = new PhantomReference(obj, phantomQueue);
		obj = null;
		System.gc();
		Thread.sleep(1000);
		System.out.println("the first time gc ");
		if (obj == null){
			System.out.println("obj is null");
		}else{
			System.out.println("obj is still live ");
		}

		obj = null;
		System.gc();
		Thread.sleep(1000);
		System.out.println("the second time gc ");
		if (obj == null){
			System.out.println("obj is null");
		}else{
			System.out.println("obj is still live ");
		}

		/*
			CanReliveObj finalize method called
			the first time gc
			obj is still live
			PhantomTraceReliveObj is died
			the second time gc
			obj is null
		 */
	}
}

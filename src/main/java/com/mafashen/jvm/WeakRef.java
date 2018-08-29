package com.mafashen.jvm;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class WeakRef {

	public static class User{
		public int id ;
		public String name;

		public User(int id, String name) {
			this.id = id;
			this.name = name;
		}

		@Override
		public String toString() {
			return "User{" +
					"id=" + id +
					", name='" + name + '\'' +
					'}';
		}
	}

	static ReferenceQueue<User> softQueue = null;

	public static class CheckWeakReferenceQueue extends Thread{

		@Override
		public void run() {
			while (true){
				if (softQueue != null){
					UserWeakReference ref = null;
					try {
						ref = (UserWeakReference) softQueue.remove();
						if (ref != null){
							System.out.println("user id " + ref.uid + " is deleted");
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private static class UserWeakReference extends WeakReference{
		public int uid ;
		public UserWeakReference(User referent, ReferenceQueue q) {
			super(referent, q);
			uid = referent.id;
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Thread checkThread = new CheckWeakReferenceQueue();
		checkThread.setDaemon(true);
		checkThread.start();

		User u = new User(1, "mafashen");
		softQueue = new ReferenceQueue<>();
		UserWeakReference userSoftReference = new UserWeakReference(u , softQueue);
		u = null;

		System.out.println(userSoftReference.get());
		System.gc();
		System.out.println("After GC : " + userSoftReference.get());

	}
}

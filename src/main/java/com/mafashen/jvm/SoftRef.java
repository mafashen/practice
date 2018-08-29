package com.mafashen.jvm;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

public class SoftRef {

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

	public static class CheckSoftReferenceQueue extends Thread{

		@Override
		public void run() {
			while (true){
				if (softQueue != null){
					UserSoftReference ref = null;
					try {
						ref = (UserSoftReference) softQueue.remove();
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

	public static void main(String[] args) throws InterruptedException {
		Thread checkThread = new CheckSoftReferenceQueue();
		checkThread.setDaemon(true);
		checkThread.start();

		User u = new User(1, "mafashen");
		softQueue = new ReferenceQueue<>();
		UserSoftReference userSoftReference = new UserSoftReference(u , softQueue);
		u = null;

		System.out.println(userSoftReference.get());
		System.gc();
		System.out.println("First time After GC : " + userSoftReference.get());

		byte[] bytes = new byte[1024 * 929 * 7];
		System.gc();
		System.out.println("Second time After GC : " + userSoftReference.get());
	}

	private static class UserSoftReference extends SoftReference{
		public int uid ;
		public UserSoftReference(User referent, ReferenceQueue q) {
			super(referent, q);
			uid = referent.id;
		}
	}
}

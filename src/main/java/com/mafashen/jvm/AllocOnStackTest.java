package com.mafashen.jvm;

/**
 * 栈上分配
 */
public class AllocOnStackTest {

	public static class User{
		public int id = 0;
		public String name = "";
//		public byte[] bytes = null;
	}

	public static void alloc(){
		User user = new User();
		user.id = 5;
		user.name = "name";
//		user.bytes = new byte[1];
	}

	//-Xms10m -Xmx10m -server -XX:+DoEscapeAnalysis -XX:+PrintGC -XX:-UseTLAB -XX:+EliminateAllocations
	//开启逃逸分析与栈上分配,限定堆大小,不会发生gc,关闭逃逸分析或者栈上分配,会出现大量GC
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		for (int i = 0; i < 1000000000; i++) {
			alloc();
		}
		System.out.println(System.currentTimeMillis() - start);
	}
}

package com.mafashen.jvm.invoke;

/**
 * 动态分派
 */
public class DyamicDispatch {
	
	static abstract class Human{
		protected abstract void sayHello();
	}
	
	static class Man extends Human{

		@Override
		protected void sayHello() {
			System.out.println("sayHello man");
		}}
	
	static class Woman extends Human {

		@Override
		protected void sayHello() {
			System.out.println("sayHello woman");
		}}
	
	
	public static void main(String[] args) {
		Human man = new Man();
		Human woman = new Woman();
		
//		invokevirtual #22                 // Method jvmtest/chapter8/DyamicDispatch$Human.sayHello:()V
		man.sayHello();
//		invokevirtual #22                 // Method jvmtest/chapter8/DyamicDispatch$Human.sayHello:()V
		woman.sayHello();
		
		man = new Woman();
//		invokevirtual #22                 // Method jvmtest/chapter8/DyamicDispatch$Human.sayHello:()V
		man.sayHello();
	}
}

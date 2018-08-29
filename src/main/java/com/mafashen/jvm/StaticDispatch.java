package com.mafashen.jvm;

/**
 *	静态分派 , 方法重载时,虚拟机通过静态类型作为判断依据决定使用哪个重载版本
 */
public class StaticDispatch {

	static class Human{}
	
	static class Man extends Human{}
	
	static class Woman extends Human {}
	
	public void sayHello(Human human) {
		System.out.println("hello , guy!");
	}
	
	public void sayHello(Man man) {
		System.out.println("hello , man!");
	}
	
	public void sayHello(Woman woman) {
		System.out.println("hello , woman!");
	}
	
	public static void main(String[] args) {
		Human man = new Man();
		Human woman = new Woman();
		
		StaticDispatch staticDispatch = new StaticDispatch();
		
		staticDispatch.sayHello(man);		//hello , guy!
/**
 * 		26: invokevirtual #51                 // Method sayHello:(Ljvmtest/chapter8/StaticDispatch$Human;)V
        31: invokevirtual #51                 // Method sayHello:(Ljvmtest/chapter8/StaticDispatch$Human;)V
 */
		staticDispatch.sayHello(woman);		//hello , guy!

		staticDispatch.sayHello((Man) man);
		staticDispatch.sayHello((Woman) woman);
	}
}

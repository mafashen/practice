package com.mafashen.jvm.invoke;

/**
 *	静态分派 , 方法重载时,虚拟机通过静态类型作为判断依据决定使用哪个重载版本
 */
public class StaticDispatch {

	static class Human{}
	
	static class Man extends Human{}
	
	static class Woman extends Human {}
	
	public void sayHello(Human human) {
		System.out.println("sayHello , guy!");
	}
	
	public void sayHello(Man man) {
		System.out.println("sayHello , man!");
	}
	
	public void sayHello(Woman woman) {
		System.out.println("sayHello , woman!");
	}
	
	public static void main(String[] args) {
		Human man = new Man();
		Human woman = new Woman();
		
		StaticDispatch staticDispatch = new StaticDispatch();
		
		staticDispatch.sayHello(man);		//sayHello , guy!
/**
 * 		26: invokevirtual #51                 // Method sayHello:(Ljvmtest/chapter8/StaticDispatch$Human;)V
        31: invokevirtual #51                 // Method sayHello:(Ljvmtest/chapter8/StaticDispatch$Human;)V
 */
		staticDispatch.sayHello(woman);		//sayHello , guy!

		staticDispatch.sayHello((Man) man);
		staticDispatch.sayHello((Woman) woman);
	}
}

package com.mafashen.jvm;

public class NotInitialization {

	public static void main(String[] args) {
		System.out.println(ConstClass.HELLOWORLD);
		System.out.println(SubClass.value);
		SuperClass[] superClasses = new SuperClass[10];
/*
hello world
[Loaded com.mafashen.jvm.SuperClass from file:/Users/mafashen/IdeaProjects/practice/target/classes/]
[Loaded com.mafashen.jvm.SubClass from file:/Users/mafashen/IdeaProjects/practice/target/classes/]
SpuerClass init
123
*/
	}
}

/**
 * 被动使用类字段演示,常量在编译阶段会存入调用类的常量池,本质上并没有直接引用到定义常量的类,因此不会触发定义常量的类的初始化
 */
class ConstClass{
	public static final String HELLOWORLD = "hello world";
	
	static {
		System.out.println("ConstClass init");
	}
}

/**
 *	被动引用类字段演示:通过数组定义来引用类,不会触发此类的初始化
 */
class SuperClass{
	static {
		System.out.println("SpuerClass init");
	}
	
	public static int value = 123;
}

class SubClass extends SuperClass{
	static {
		System.out.println("SubClass init");
	}
}
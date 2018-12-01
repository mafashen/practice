package com.mafashen.jvm.invoke;

import java.io.Serializable;

/**
 *	静态分派 根据实参与方法形参 自动安全的类型转换 
 */
public class OverloadTest {

	public static void sayHello(char c) {
		System.out.println("sayHello , char");
	}
	
	public static void sayHello(int i) {
		System.out.println("sayHello , int");	//'c'自动转换为int
	}
	
	public static void sayHello(long l) {
		System.out.println("sayHello , long");	//'c'自动转换为int,再自动转换为long
	}

	public static void sayHello(float i) {
		System.out.println("sayHello , float");	//'c'自动转换为int
	}

	public static void sayHello(double i) {
		System.out.println("sayHello , double");	//'c'自动转换为int
	}

	public static void sayHello(Character c) {
		System.out.println("sayHello , Character");	//'c'自动装箱为Character
	}
	
	public static void sayHello(Serializable s) {
		System.out.println("sayHello , Serializable");	//'c'自动装箱为Character,再自动转换为Character实现的接口类型
	}
	
	public static void sayHello(Object o) {
		System.out.println("sayHello , Object");	//'c'自动装箱为Character,再自动转换为Character的父类类型
	}
	
	public static void sayHello(char ... c) {
		System.out.println("sayHello , char ... ");	//可变长参数方法优先级最低
	}
	
	public static void main(String[] args) {
		sayHello('c');	//Object
//		sayHello(null); //char...
	}
}

package com.mafashen.jvm;

/**
 * 类加载机制--字段解析
 */
public class FieldResolutionTest {

	interface interface1{
		int a = 0;
	}

	interface interface2 extends interface1{
		int a = 1;
	}

	interface interface3{
		int a = 2;
	}

	static class Parent implements interface2{
		public static int a = 3;
	}

	static class Sub extends Parent implements interface3{
		public static int a = 4;//字段解析时,子类同名字段将覆盖父类和接口中字段
	}

	public static void main(String[] args) {
		System.out.println(Sub.a);	//reference a id ambiguous , both Parent.a and interface3.a match
	}
}

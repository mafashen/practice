package com.mafashen.jvm;

/**
 *语法糖  自动装箱 自动拆箱
 */
public class Box {

	public static void main(String[] args) {
		Integer a = 1;
		Integer b = 2;
		Integer c = 3;
		Integer d = 3;
		Integer e = 321;
		Integer f = 321;
		Long g = 3L;
		
		System.out.println(c == d);
		System.out.println(e == f); //F
		//== 运算符在不遇到算术运算时不会自动拆箱
		System.out.println(c == (a + b));
		//equals不处理数据转型的关系
		System.out.println(c.equals(a + b));
		System.out.println(g == (a + b));
		System.out.println(g.equals(a + b)); //F
	}
}

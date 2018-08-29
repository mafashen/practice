package com.mafashen.jvm;

public class TestStatic {

	static {		//静态语句块中只能访问到在前面定义的变量
		i = 0;
//		System.out.println(i);		非法向前引用 , 不能在定义前引用它
	}
	
	static int i = 1;
	
	public static void main(String[] args) {
		System.out.println(i);
	}
}

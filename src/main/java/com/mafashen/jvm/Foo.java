package com.mafashen.jvm;

/**
 * @author mafashen
 * created on 2018/11/16.
 */
public class Foo {
	public static void main(String[] args) {
		boolean fag = true;
		//ifeq
		if (fag) {
			System.out.println("Hello, Java!");
		}
		//if_icmpne
		if (fag == true) {
			System.out.println("Hello, JVM!");
		}

		System.out.println();
	}
}

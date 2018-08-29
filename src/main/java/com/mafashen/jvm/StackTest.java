package com.mafashen.jvm;

public class StackTest {

	public static void main(String[] args) {
		for (int i = 0; i < 10000000; i++) {
			SimpleClass temp = new SimpleClass();
			System.out.println(i);
		}
	}

	static class SimpleClass{
		int i;
	}
}

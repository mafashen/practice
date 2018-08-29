package com.mafashen.java;

public class FloatTest {

	public static void main(String[] args) {
		float f = Float.MAX_VALUE - 1;

		for (;f > 0; f--) {
			if (f + 1 == f){
				System.out.printf("%6f\n",f);
//				break;
			}
		}
	}
}

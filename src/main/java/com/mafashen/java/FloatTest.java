package com.mafashen.java;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;

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

	@Test
	public void test(){
		List<Integer> list = Arrays.asList(21083, 21027, 21068, 25864, 26008, 25865);
		List<Integer> list1 = Arrays.asList(20441, 20454, 20455, 20456, 20457, 20458);

		System.out.println(CollectionUtils.subtract(list,list1));
	}

	@Test
	public void testNaN(){
		Float zero = 0f;
		Float negativeZero = Float.intBitsToFloat(0x80000000);

		Float infinity =  1 / zero;
		Float negativeInfinity = 1 / negativeZero;

		System.out.println(Integer.toHexString(Float.floatToIntBits(infinity))); //7f800000
		System.out.println(Integer.toHexString(Float.floatToIntBits(negativeInfinity))); //ff800000
		float NaN = Float.intBitsToFloat(0x7F800001);
		System.out.println(NaN); //NaN
		System.out.println(NaN < 1f); //false
		System.out.println(NaN >= 1f); //false
		System.out.println(NaN == zero); //false
		System.out.println(NaN != zero); //true
	}

	private void testStackVarLength(){
		boolean boo = true; //iconst_1 将int型1推送至栈顶
		boolean boo2 = false; //iconst_0 将int型0推送至栈顶
		byte b = 1; //iconst_1
		char c = 1; //iconst_1
		short s = 1; //iconst_1
		int i = 1; //iconst_1
		long l = 1; //lconst_1
		float f = 1; //fconst_1
		double d = 1; //dconst_1
		String str = "str";
		System.out.println(b);
	}

	@Test
	public void testBoolean(){
		Boolean flag = null;
		if (flag){
			System.out.println("true");
		}else{
			System.out.println("false");
		}
	}

}

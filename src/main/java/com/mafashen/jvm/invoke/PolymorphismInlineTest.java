package com.mafashen.jvm.invoke;

/**
 * 单态内联,多态内联
 * @author mafashen
 * created on 2018/11/18.
 */
public class PolymorphismInlineTest {

	public static abstract class 乘客 {
		abstract void 出境 ();
	}

	static class 中国人 extends 乘客 {
		@Override void 出境 () {}
	}
	static class 外国人 extends 乘客 {
		@Override void 出境 () {}
	}

	// Run with: java -XX:CompileCommand='dontinline,*. 出境' 乘客
	public static void main(String[] args) {
		乘客 a = new 中国人 ();
		乘客 b = new 外国人 ();

		long current = System.currentTimeMillis();
		for (int i = 1; i <= 2_000_000_000; i++) {
			if (i % 100_000_000 == 0) {
				long temp = System.currentTimeMillis();
				System.out.println(temp - current);
				current = temp;
			}
			乘客 c = (i < 1_000_000_000) ? a : b;
			c. 出境 ();
		}
		//95,130,122,124,134,181,182,170,140,118,160,155,159,160,159,155,158,154,156,155
		//109,128,124,129,139,211,186,153,143,134,171,158,156,163,160,157,159,162,160,163
	}
}

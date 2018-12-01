package com.mafashen.jvm.code;

/**
 * @author mafashen
 * created on 2018/11/22.
 */
public class SwitchTest {

	public static void main(String[] args) {
		switch (args[0]){
			case "A":
				System.out.println("A");
				break;
			case "B":
				System.out.println("B");
				break;
			default:
				break;
		}
	}
/*
6: aload_1
7: invokevirtual #2                  // Method java/lang/String.hashCode:()I
10: lookupswitch  { // 2
			  65: 36
			  66: 50
		 default: 61
	}
36: aload_1
37: ldc           #3                  // String A
39: invokevirtual #4                  // Method java/lang/String.equals:(Ljava/lang/Object;)Z
42: ifeq          61
45: iconst_0
46: istore_2
47: goto          61
由于每个 case 所截 获的字符串都是常量值，因此，Java 编译器会将原来的字符串 switch 转换为 int 值 switch，比较所 输入的字符串的哈希值。
由于字符串哈希值很容易发生碰撞，因此，我们还需要用 String.equals 逐个比较相同哈希值的字符 串。
 */
}

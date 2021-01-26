package com.mafashen.jvm.code;

/**
 * @author mafashen
 * created on 2018/11/22.
 */
public class SwitchTest {

	public static final String CASE_A = "A";
	public static final String CASE_B = "B";

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
		switch (args[0]){	//由于比较的是字符串,即使case中换成常量,还是会有hashcode与equals调用,不过上面的case写法,本身也是字符串常量
			case CASE_A:
				System.out.println("A");
				break;
			case CASE_B:
				System.out.println("B");
				break;
			default:
				break;
		}
	}
/*
6: aload_1
7: invokevirtual #2                  // Method java/lang/String.hashCode:()I	调用hashcode()
10: lookupswitch  { // 2
			  65: 36
			  66: 50
		 default: 61
	}
36: aload_1
37: ldc           #3                  // String A
39: invokevirtual #4                  // Method java/lang/String.equals:(Ljava/lang/Object;)Z	哈希冲突确保一致
42: ifeq          61
45: iconst_0
46: istore_2
47: goto          61
50: aload_1
51: ldc           #5                  // String B
53: invokevirtual #4                  // Method java/lang/String.equals:(Ljava/lang/Object;)Z
56: ifeq          61
59: iconst_1
60: istore_2
61: iload_2
62: lookupswitch  { // 2
			   0: 88
			   1: 99
		 default: 110
	}
88: getstatic     #6                  // Field java/lang/System.out:Ljava/io/PrintStream;
91: ldc           #3                  // String A
93: invokevirtual #7                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
96: goto          110
99: getstatic     #6                  // Field java/lang/System.out:Ljava/io/PrintStream;
102: ldc           #5                  // String B
104: invokevirtual #7                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
107: goto          110
110: return
由于每个 case 所截 获的字符串都是常量值，因此，Java 编译器会将原来的字符串 switch 转换为 int 值 switch，比较所 输入的字符串的哈希值。
由于字符串哈希值很容易发生碰撞，因此，我们还需要用 String.equals 逐个比较相同哈希值的字符 串。
 */
}

package com.mafashen.jvm.reflect;

import java.lang.reflect.Method;

/**
 * @author mafashen
 * created on 2018/11/19.
 */
public class ReflectElapsedTest {

	public static void target(int i){

	}

	//RunWith :
	// -Djava.lang.Integer.IntegerCache.high=128  Integer缓存池最大值
	// -Dsun.refect.noInfation=true		反射调用直接生产字节码调用,没有java->C++->java额外开销				//~250ms
	public static void main(String[] args) throws Exception {
		//直接调用
//		long current = System.currentTimeMillis(); for (int i = 1; i <= 2_000_000_000; i++) {
//			if (i % 100_000_000 == 0) {
//				long temp = System.currentTimeMillis(); System.out.println(temp - current); current = temp;
//			}
//			target(128);	//~100ms
//		}

		//反射调用
		//方法的反射调用会带来不少性能开销，原因主要有三个:
		// 变长参数方法导致的 Object 数组，
		// 基本 类型的自动装箱、拆箱，
		// 还有最重要的方法内联
		Class<?> clz = Class.forName("com.mafashen.jvm.reflect.ReflectElapsedTest");
		Method method = clz.getMethod("target", int.class);
		method.setAccessible(true); //~180ms 不做方法权限检查

		//虚拟机关于每个调用能够记录的类型数目默认为2 -- XX:TypeProfleWidth
		polluteProfle(); //~1100-1200ms 消除内联且逃逸分析不再起作用
		//只要没有完全内联，就会将看似不逃逸的对象通过参数传递出去。即时编译器不知道所调用的 方法对该对象有没有副作用，所以会将其判定为逃逸

		//JIT即时编译,方法内联
		long current = System.currentTimeMillis(); for (int i = 1; i <= 2_000_000_000; i++) {
			if (i % 100_000_000 == 0) {
				long temp = System.currentTimeMillis(); System.out.println(temp - current); current = temp;
			}
			method.invoke(null, 128); //~360-400ms
		}
	}
	public static void polluteProfle() throws Exception {
		//Method.invoke一直会被内联，但是它里面的MethodAccesor.invoke则 不一定
		//实际上，在C2编译之前循环代码已经运行过非常多次，也就是说MethodAccesor.invoke已经看到 多次调用至target()的动态实现。
		// 在profle里会显示为有target1，有target2，但是profle不完整， 即还有一大部分的调用者类型没有记录。
		// 这时候C2会选择不inline这个MethodAccesor.invoke调用，直接做虚调用
		Method method1 = ReflectElapsedTest.class.getMethod("target", int.class);
		Method method2 = ReflectElapsedTest.class.getMethod("target", int.class); //target ~190ms
		System.out.println(method1.equals(method2)); //true
		System.out.println(method1 == method2);
		for(int i=0;i<2000;i++){
			method1.invoke(null, 0);
			method2.invoke(null, 0);
		}
	}

	public static void target1(int i) { }
	public static void target2(int i) { }
}
/*
 0 ldc #2 <com.mafashen.jvm.reflect.ReflectElapsedTest>
 2 invokestatic #3 <java/lang/Class.forName>
 5 astore_1
 6 aload_1
 7 ldc #4 <target>
 9 iconst_1
10 anewarray #5 <java/lang/Class>
13 dup
14 iconst_0
15 getstatic #6 <java/lang/Integer.TYPE>
18 aastore
19 invokevirtual #7 <java/lang/Class.getMethod>
22 astore_2
23 invokestatic #8 <java/lang/System.currentTimeMillis>
26 lstore_3
27 iconst_1
28 istore 5
30 iload 5
32 ldc #9 <2000000000>
34 if_icmpgt 88 (+54)
37 iload 5
39 ldc #10 <100000000>
41 irem
42 ifne 63 (+21)
45 invokestatic #8 <java/lang/System.currentTimeMillis>
48 lstore 6
50 getstatic #11 <java/lang/System.out>
53 lload 6
55 lload_3
56 lsub
57 invokevirtual #12 <java/io/PrintStream.println>
60 lload 6
62 lstore_3
63 aload_2 					将第三个引用类型本地变量推送至栈顶
64 aconst_null				将null推送至栈顶
65 iconst_1					将int型1推送至栈顶
66 anewarray #13 <java/lang/Object>	创建一个引用类型数组,并将其引用推送至栈顶
69 dup						复制栈顶数值,并将复制值压入栈顶
70 iconst_0					将int型0推送至栈顶
71 sipush 128				将短整型值128推送至栈顶
74 invokestatic #14 <java/lang/Integer.valueOf>		调用静态方法 int自动装箱
77 aastore					将栈顶引用类型数值存入指定本地变量 存入Object数组中 invoke(Object obj, Object... args)
78 invokevirtual #15 <java/lang/reflect/Method.invoke>		调用实例方法 反射调用
81 pop						将栈顶数值弹出(不能是long/double)
82 iinc 5 by 1				将指定int型数值增加指定值
85 goto 30 (-55)
88 return
*/


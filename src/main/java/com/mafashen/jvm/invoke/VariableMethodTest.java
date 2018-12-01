package com.mafashen.jvm.invoke;

/**
 * @author mafashen
 * created on 2018/11/18.
 */
public class VariableMethodTest {

	public void variableMethod(Object o, Object ... objects){
		System.out.println("variable method 1");
	}

	public void variableMethod(String s, Object o, Object ... objects){
		System.out.println("variable method 2");
	}

	public static void main(String[] args) {
		VariableMethodTest test = new VariableMethodTest();
		test.variableMethod(null, 1); //2 由于 String 是 Object 的子类，因此 Java 编译器会 认为第二个方法更为贴切。
		test.variableMethod(null, 1, 2); //2
		test.variableMethod(null, new Object[]{1}); //1

		Object obj = new String("test");
		test.variableMethod(obj, 1); //1 静态分派,通过静态类型确定

		String str = "test";
		test.variableMethod(str,1); //2 进一步说明是静态分派是通过静态类型确定的

/**
 1. 在不考虑对基本类型自动装拆箱(auto-boxing，auto-unboxing)，以及可变长参数的情况下 选取重载方法;
 2. 如果在第 1 个阶段中没有找到适配的方法，那么在允许自动装拆箱，但不允许可变长参数的情 况下选取重载方法;
 3. 如果在第 2 个阶段中没有找到适配的方法，那么在允许自动装拆箱以及可变长参数的情况下选 取重载方法。
 */
	}
}

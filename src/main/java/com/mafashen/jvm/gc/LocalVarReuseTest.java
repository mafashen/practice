package com.mafashen.jvm.gc;

/**
 * 局部变量表中的变量也是重要的垃圾回收根节点,只要被局部变量表中直接或间接引用的对象都是不会被回收的.
 * 局部变量对垃圾回收的影响.
 */
public class LocalVarReuseTest {

	private void localVar1(){
		int a = 0;
		System.out.println(a);
		int b = 0;
	}

	private void localVar2(){
		{
			int a = 0;
			System.out.println(a);
		}
		//如果一个局部变量过了其作用域,那么在其作用域之后申明的新的局部变量就很有可能会复用过期局部变量的槽位,从而达到节省资源的目的.
		int b = 0;	//复用了局部变量a的槽位
	}

	private void exceptionTable(){
		int a = 0;
		try{
			int b = 0;
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			int c = 0;
		}
	}
	public static void main(String[] args) {

	}
}

package com.mafashen.jvm.invoke;

/**
 * @author mafashen
 * created on 2018/11/18.
 */
public class JvmInvokeTest {

	public interface Person{

		public void sayHello();

		public default void defaultMethod(){
			System.out.println("interface default method");
		}
	}

	static class Father implements Person{

		public Father(){
			System.out.println("father special method");
		}

		public static void staticMethod(){
			System.out.println("father static method");
		}

		public void virtualMethod(){
			System.out.println("father virtual method");
		}

		public void finalMethod(){
			System.out.println("father final method");
		}

		@Override
		public void sayHello() {
			System.out.println("i'm father");
		}
	}

	static class Son extends Father{

		public Son(){
			System.out.println("son special method");
		}

		public static void staticMethod(){
			System.out.println("son static method");
		}

		public void virtualMethod(){
			System.out.println("son virtual method");
		}

		@Override
		public void sayHello() {
			System.out.println("i'm son");
		}
	}

	public static void main(String[] args) {
		Father father = new Father();
		father.staticMethod();
		father.virtualMethod();
		father.finalMethod();
		father.sayHello();
		System.out.println();

		Son son = new Son();
		son.staticMethod();
		son.virtualMethod();
		son.finalMethod();
		son.sayHello();
		System.out.println();

		Father son2 = new Son();
		son2.staticMethod();
		son2.virtualMethod();
		son2.sayHello();
		son2.finalMethod();
		son2.defaultMethod();
		System.out.println();

		Person person = new Father();
		person.sayHello();	//invokeinterface
	}
}

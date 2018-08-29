package com.mafashen.jvm;

/**
 * 多分派
 * 静态多分派
 * 动态单分派
 */
public class MultiDispatch {

	static class QQ{}

	static class TencentQQ extends QQ{}
	
	static class _360{}
	
	static class Father{
		
		public void hardChoose(QQ arg) {
			System.out.println("father choose qq");
		}

		public void hardChoose(TencentQQ arg) {
			System.out.println("father choose tencent qq");
		}

		public void hardChoose(_360 arg) {
			System.out.println("father choose 360");
		}
	}
	
	static class Son extends Father {
		
		public void hardChoose(QQ arg) {
			System.out.println("son choose qq");
		}

		public void hardChoose(TencentQQ arg) {
			System.out.println("son choose tencent qq");
		}

		public void hardChoose(_360 arg) {
			System.out.println("son choose 360");
		}
	}
	
	public static void main(String[] args) {
		Father father = new Father();
		Father son = new Son();
		
		/**
		 * invokevirtual #25                 
		 * // Method jvmtest/chapter8/MultiDispatch$Father.hardChoose:(Ljvmtest/chapter8/MultiDispatch$_360;)V
		 */
		father.hardChoose(new _360());
		
		/**
		 * invokevirtual #32                 
		 * // Method jvmtest/chapter8/MultiDispatch$Father.hardChoose:(Ljvmtest/chapter8/MultiDispatch$QQ;)V
		 */
		son.hardChoose(new QQ());

		QQ tencentQQ = new TencentQQ();
		father.hardChoose(tencentQQ);
		son.hardChoose(tencentQQ);
	}
}

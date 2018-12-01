package com.mafashen.jvm.code;

import java.util.ArrayList;
import java.util.List;

public class ExceptionTableTest {

	 int inc(){
//		int x ;

		try{
//			x = 1;
//			可能出现异常操作
			return 1;
		}
//		catch(IndexOutOfBoundsException e){
//			x = 2;
////			可能出现异常操作
//			return x;
//		} catch(Exception e){
//			x = 2;
////			可能出现异常操作
//			return x;
//		}
		finally{
////			x = 3;
////			return x;
		}
	}

	int inc1(){
		int x ;
		List<Long> list = new ArrayList<>();
		try{
			x = 1;
//			可能出现异常操作
			int[] a = new int[1];
			int aa = a[2];
			return x;
		}catch(IndexOutOfBoundsException e){
			x = 2;
//			可能出现异常操作
			int a = 1/0;
			return x;
		}finally{
			x = 3;
			return x;
		}
	}

	public int aaa() {
		int x = 1;

		try {
			return ++x;
		} catch (Exception e) {

		}
		finally {
			++x;
		}
		return x;
	}

	final static int x = 1;
	class Test {
		public int aaa() {
			int x = 1;

			try {
				return ++x;
			} catch (Exception e) {

			} finally {
				++x;
			}
			return x;
		}

	}

	private int testPrivate(){
		int i = 0;
		return i;
	}

	public static void main(String[] args) {
//		System.out.println(new ExceptionTableTest().inc());
		System.out.println(new ExceptionTableTest().inc());
		System.out.println(new ExceptionTableTest().aaa());
		System.out.println(new ExceptionTableTest().testPrivate());		//invokespecial
	}
}

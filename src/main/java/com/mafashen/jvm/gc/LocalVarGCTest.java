package com.mafashen.jvm.gc;

/**
 * 局部变量表Slot复用对垃圾收集的影响
 */
public class LocalVarGCTest {

	//byte[] 被 a 引用 , 无法回收这快内存;
	// 局部变量表Slot没有被其他变量所复用,所以作为GC Roots一部分的局部变量表仍然保持着对它的关联
	private void localVarGC1(){
		byte[] a = new byte[6*1024*1024];
		System.gc();	//JIT编译时能够回收
	}

	//垃圾回收前,a = null , 使byte[] 是去强引用,可以回收
	private void localVarGC2(){
		byte[] a = new byte[6*1024*1024];
		a = null;//JIT编译后,此操作会被消除掉
		System.gc();
	}

	//在垃圾回收前,先使局部变量a失效,虽然变量a已经离开了作用域,但是变量a依然存在于局部变量表中,并且也指向这块byte[],byte[]依然无法被回收.
	private void localVarGC3(){
		{
			byte[] a = new byte[6 * 1024 * 1024];
		}
		System.gc();
	}

	//在垃圾回收前,不仅使变量a失效,更是申明了变量c,使变量c复用了变量a的槽位,由于变量a此时被销毁,故可以回收byte[]
	private void localVarGC4(){
		{
			byte[] a = new byte[6 * 1024 * 1024];
		}
		int c = 10;
		System.gc();
	}

	//先调用localVarGC1(),在localVarGC1()中并没有释放byte[],但在localVarGC1()返回后,它的帧栈被销毁,自然也包含了帧栈中的所有局部变量
	//故byte[]是去引用,在localVarGC5()的垃圾回收中被回收.
	private void localVarGC5(){
		localVarGC1();
		System.gc();
	}

	public static void main(String[] args) {
		LocalVarGCTest test = new LocalVarGCTest();
		test.localVarGC1();
	}
}

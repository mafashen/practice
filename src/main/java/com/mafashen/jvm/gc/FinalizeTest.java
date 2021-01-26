package com.mafashen.jvm.gc;

public class FinalizeTest {
	private static FinalizeTest obj ;

	/*
	finalize方法只会被调用一次,是对象自求的最后机会
		CanReliveObj finalize method called
		the first time gc
		obj is still live
		the second time gc
		obj is null
		要真正宣告一个对象死亡，至少要经历两次标记过程：如果对象在进行可达性分析后发现没有与GC Roots相连接的引用链，
		那它将会被 第一次标记并且进行一次筛选，筛选的条件是此对象是否有必要执行finalize方法。
			当对象没有覆盖finalize（）方法，
			或者finalize（）方法已经被虚拟机调用过，
		虚拟机将这两种情况都视为“没有必要执行”。如果这个对象被判定为有必要执行finalize（）方法，那么这个对象将会放置在一个叫做F-Queue的队列之中，
		并在稍后由一个由虚拟机自动建立的、低优先级的Finalizer线程去执行它。
		finalize（）方法是对象逃脱死亡命运的最后一次机会，稍后GC将对F-Queue中的对象进行第二次小规模的标记，
		如果对象要在finalize（）中成功拯救自己——只要重新与引用链上的任何一个对象建立关联即可，
		譬如把自己（this关键字）赋值给某个类变量或者对象的成员变量，那在第二次标记时它将被移除出“即将回收”的集合；
		如果对象这时候还没有逃脱，那基本上它就真的被回收了。
		不推荐使用finalize方法,finalize低优先级,调用时间不确定,再try-finally中释放资源
	 */
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		System.out.println("CanReliveObj finalize method called ");
		obj = this;
	}

	public static void main(String[] args) throws InterruptedException {
		obj = new FinalizeTest();
		obj = null;
		System.gc();
		Thread.sleep(1000);
		System.out.println("the first time gc ");
		if (obj == null){
			System.out.println("obj is null");
		}else{
			System.out.println("obj is still live ");
		}

		obj = null;
		System.gc();
		Thread.sleep(1000);
		System.out.println("the second time gc ");
		if (obj == null){
			System.out.println("obj is null");
		}else{
			System.out.println("obj is still live ");
		}
	}
}

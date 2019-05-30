package com.mafashen.java;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

import com.mafashen.jvm.Foo;

/**
 * @author mafashen
 * created on 2019/5/23.
 */
public class ReferenceTest {

	public static void main(String[] args) {
		ReferenceQueue<Foo> referenceQueue = new ReferenceQueue<>();

		Foo foo = new Foo();
		WeakReference<Foo> sr1 = new WeakReference<Foo>(foo, referenceQueue);
		Foo foo2 = new Foo();
		WeakReference<Foo> sr2 = new WeakReference<Foo>(foo2, referenceQueue);

		foo = null;
		foo2 = null;
		System.out.println("before gc ");
		System.out.println("\t isEnqueued : " + sr1.isEnqueued() + " \t " + sr2.isEnqueued());
		System.out.println("\t get : " + sr1.get() + " \t" + sr2.get());


		System.gc();

		System.out.println("After gc ");
		System.out.println("\t isEnqueued : " + sr1.isEnqueued() + " \t " + sr2.isEnqueued());
		System.out.println("\t get : " + sr1.get() + " \t" + sr2.get());
	}
}

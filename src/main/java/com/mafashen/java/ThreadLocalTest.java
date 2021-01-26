package com.mafashen.java;

import java.lang.ref.WeakReference;

/**
 * @author mafashen
 * @since 2021-01-10.
 */
public class ThreadLocalTest {

    public static void main(String[] args) {
        ThreadLocal<String> local = new ThreadLocal<>();
        WeakReference<String> weakReference = new WeakReference<>("weak");

        Thread thread = Thread.currentThread();

        ThreadLocal<String> inheritalbleLocal = new InheritableThreadLocal<>();

        thread = Thread.currentThread();

        local.set("local");
        inheritalbleLocal.set("inheritalbleLocal");
        System.gc();

        System.out.println(local.get());
        System.out.println(weakReference.get());


    }
}

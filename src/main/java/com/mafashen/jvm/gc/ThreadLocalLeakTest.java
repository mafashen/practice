package com.mafashen.jvm.gc;

/**
 * @author mafashen
 * @since 2019-10-24.
 */
public class ThreadLocalLeakTest {

    // -Xmx10m -XX:+PrintGCDetails
    public static void main(String[] args) {
        while(true){
            test();
        }
    }

    static void test(){
        ThreadLocal<byte[]> local = new ThreadLocal<>();
        local.set(new byte[1024*1024]);
        // 如果不调用remove,虽然方法结束了,但是因为主线程没有over, Thread => ThreadLocalMap => Entry 的引用链始终存在, GC不能有效回收内存,造成内存泄漏
//        local.remove();
    }
}

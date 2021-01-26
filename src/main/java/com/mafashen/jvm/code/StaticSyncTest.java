package com.mafashen.jvm.code;

/**
 * @author mafashen
 * @since 2019-11-12.
 */
public class StaticSyncTest {

    public static void main(String[] args) {
       staticSyncMethod();
    }

    // 静态同步方法的加锁与释放,并不是预想中的在调用方法前后加入monitorentry 和 monitorexit 指令,而是由JVM保证的
    public static synchronized void staticSyncMethod(){
        System.out.println("staticSyncMethod");
    }
}

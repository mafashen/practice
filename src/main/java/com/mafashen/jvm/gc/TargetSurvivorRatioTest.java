package com.mafashen.jvm.gc;

import java.util.ArrayList;
import java.util.List;

/**
 * 对象动态年龄判断
 * @author mafashen
 * @since 2021-01-04.
 */
public class TargetSurvivorRatioTest {

    public static final int _1M = 1024 * 1024;
    public static final int _1K = 1024 ;

    /**
     * -Xms20m -Xmx20m -Xmn10m -XX:+PrintGCDetails -XX:+PrintHeapAtGC -XX:+UseSerialGC -XX:+PrintTenuringDistribution -XX:TargetSurvivorRatio=90
     * @param args
     */
    public static void main(String[] args) {
        arrayTest();
    }

    public static void listTest(){
        List<byte[]> list2 = new ArrayList<>();
        List<byte[]> list3 = new ArrayList<>();
        List<byte[]> list4 = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0)
                list2.add(new byte[_1M]);
            if (i % 3 == 0)
                list3.add(new byte[_1M]);
            if (i % 4 == 0)
                list4.add(new byte[_1M]);
        }
    }

    /**
     * {Heap before GC invocations=0 (full 0):
     *  def new generation   total 9216K, used 7455K [0x00000007bec00000, 0x00000007bf600000, 0x00000007bf600000)
     *   eden space 8192K,  91% used [0x00000007bec00000, 0x00000007bf347e70, 0x00000007bf400000)
     *   from space 1024K,   0% used [0x00000007bf400000, 0x00000007bf400000, 0x00000007bf500000)
     *   to   space 1024K,   0% used [0x00000007bf500000, 0x00000007bf500000, 0x00000007bf600000)
     *  tenured generation   total 10240K, used 0K [0x00000007bf600000, 0x00000007c0000000, 0x00000007c0000000)
     *    the space 10240K,   0% used [0x00000007bf600000, 0x00000007bf600000, 0x00000007bf600200, 0x00000007c0000000)
     *  Metaspace       used 2685K, capacity 4486K, committed 4864K, reserved 1056768K
     *   class space    used 289K, capacity 386K, committed 512K, reserved 1048576K
     * [GC (Allocation Failure) [DefNew: 7455K->499K(9216K), 0.0044221 secs] 7455K->6643K(19456K), 0.0044429 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
     * Heap after GC invocations=1 (full 0):
     *  def new generation   total 9216K, used 499K [0x00000007bec00000, 0x00000007bf600000, 0x00000007bf600000)
     *   eden space 8192K,   0% used [0x00000007bec00000, 0x00000007bec00000, 0x00000007bf400000)
     *   from space 1024K,  48% used [0x00000007bf500000, 0x00000007bf57cef0, 0x00000007bf600000)
     *   to   space 1024K,   0% used [0x00000007bf400000, 0x00000007bf400000, 0x00000007bf500000)
     *  tenured generation   total 10240K, used 6144K [0x00000007bf600000, 0x00000007c0000000, 0x00000007c0000000)
     *  一次minorGC后, 老年代使用空间增加, 对象动态年龄判断, 直接进入到老年代
     *    the space 10240K,  60% used [0x00000007bf600000, 0x00000007bfc00060, 0x00000007bfc00200, 0x00000007c0000000)
     *  Metaspace       used 2685K, capacity 4486K, committed 4864K, reserved 1056768K
     *   class space    used 289K, capacity 386K, committed 512K, reserved 1048576K
     * }
     */

    public static void arrayTest(){
        byte[] b1, b2, b3, b4;

        b1 = new byte[_1K * 800];
        b2 = new byte[_1K * 800];
        b3 = new byte[_1M * 4];
        b4 = new byte[_1M * 4];
    }
}

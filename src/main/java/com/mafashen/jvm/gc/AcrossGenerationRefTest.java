package com.mafashen.jvm.gc;

import java.util.ArrayList;
import java.util.List;

/**
 * 跨代引用导致一直CMS情况测试
 * @author mafashen
 * @since 2019-08-15.
 */
public class AcrossGenerationRefTest {

    /**
     * @args -Xms500m -Xmx500m -Xmn200m -XX:+UseConcMarkSweepGC
     * -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=90
     * -XX:+CMSScavengeBeforeRemark
     */
    public static void main(String[] args) {
        allocate();
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void allocate(){
        int size = 1024 * 1024 * 480;   //480m
        int len = size / 20 / 1024; //24 * 1024
        List<byte[]> list = new ArrayList<>();

        for (int i = 0; i < len; i++) {
            try {
                byte[] bytes = new byte[20 * 1024]; //20k
                list.add(bytes);
//                Thread.sleep(10);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
}

package com.mafashen.io;

import com.mafashen.constant.Const;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import java.lang.management.ManagementFactory;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mafashen
 * @since 2020-01-03.
 */
public class BufferTest {

    private static MBeanServer mbs = ManagementFactory. getPlatformMBeanServer() ;

    private int mark = -1;
    private int position = 0;

    public void reset(){
        int m = mark;
        if(m < 0)
            ;
//            throw new RuntimeException();
        position = m;
    }

    public void reset2(){
        if (mark < 0)
            ;
//            throw new RuntimeException();
        position = mark;
    }

    public static void testDirectBuffer(){
        ByteBuffer directBuffer = ByteBuffer.allocateDirect(Const._1M);

        for (int i = 0; i < 10; i++) {
            // 连续申请直接内存, 触发fullGC
            getMonitorObject("java.nio:type=BufferPool,name=direct" );
            getMonitorObject("*:type=ReferenceQueue,name=*");
            ByteBuffer directBufferC = ByteBuffer.allocateDirect(Const._1M);
        }
    }

    private static void getMonitorObject(String name) {
        try {
            ObjectName objectName = new ObjectName(name);
            MBeanInfo info = mbs.getMBeanInfo(objectName) ;
            for(MBeanAttributeInfo i : info.getAttributes()) {
                System.out .println(i.getName() + ":" + mbs.getAttribute(objectName , i.getName()));
            }
        } catch (MalformedObjectNameException | ReflectionException | InstanceNotFoundException | IntrospectionException | AttributeNotFoundException | MBeanException e) {
            e.printStackTrace();
        }
    }

    /**
     * -XX:MaxDirectMemorySize=5m -XX:+PrintGCDetails -XX:+PrintGCTimeStamps
     * @param args
     */
    public static void main(String[] args) {
        testDirectBuffer();
    }
}

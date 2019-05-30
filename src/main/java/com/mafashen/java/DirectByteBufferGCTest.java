package com.mafashen.java;

import java.nio.ByteBuffer;
import sun.nio.ch.DirectBuffer;

/**
 * 直接内存回收测试
 * @author mafashen
 * created on 2019/5/17.
 */
public class DirectByteBufferGCTest {

	//-XX:+PrintGCDetails -XX:+UseConcMarkSweepGC  -XX:MaxDirectMemorySize=1024M
	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * 1024 * 30);
			if (buffer.isDirect()){
				//[Full GC (System.gc()) [CMS: 0K->457K(174784K), 0.0481813 secs] 2798K->457K(253440K), [Metaspace: 2702K->2702K(1056768K)], 0.0483023 secs] [Times: user=0.02 sys=0.03, real=0.05 secs]
				//不显示调用clean,频繁触发fullGc,因为DirectBuffer在初始化时,如果已使用的堆外内存容量+申请的容量小于MaxDirectMemorySize时
				//会显示的调用System.gc()回收堆外内存
//				((DirectBuffer)buffer).cleaner().clean();
			}
		}
	}
}

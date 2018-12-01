package com.mafashen.jvm.gc;

import java.util.HashMap;
import java.util.Map;

public class PretenureSizeThreshold {

	public static final int _1K = 1024;

	public static void main(String[] args) {
		Map<Integer , byte[]> map = new HashMap<>();
		for (int i = 0; i < 5 * _1K; i++) {
			byte[] b = new byte[_1K];
			map.put(i , b);
		}
	}

/*	默认的 PretenureSizeThreshold ,对象都在 eden区分配
Heap
 def new generation   total 9792K, used 6663K [0x00000007f8e00000, 0x00000007f98a0000, 0x00000007f98a0000)
  eden space 8704K,  76% used [0x00000007f8e00000, 0x00000007f9481e40, 0x00000007f9680000)
  from space 1088K,   0% used [0x00000007f9680000, 0x00000007f9680000, 0x00000007f9790000)
  to   space 1088K,   0% used [0x00000007f9790000, 0x00000007f9790000, 0x00000007f98a0000)
 tenured generation   total 21888K, used 0K [0x00000007f98a0000, 0x00000007fae00000, 0x00000007fae00000)
   the space 21888K,   0% used [0x00000007f98a0000, 0x00000007f98a0000, 0x00000007f98a0200, 0x00000007fae00000)
 compacting perm gen  total 21248K, used 2635K [0x00000007fae00000, 0x00000007fc2c0000, 0x0000000800000000)
   the space 21248K,  12% used [0x00000007fae00000, 0x00000007fb092fc0, 0x00000007fb093000, 0x00000007fc2c0000)
 */
/*	PretenureSizeThreshold = 1000 JVM在为线程分配空间时,会优先使用TLAB区域,对于体积不大的对象,很有可能会在TLAB上先行分配
Heap
 def new generation   total 9792K, used 6647K [0x00000007f8e00000, 0x00000007f98a0000, 0x00000007f98a0000)
  eden space 8704K,  76% used [0x00000007f8e00000, 0x00000007f947de30, 0x00000007f9680000)
  from space 1088K,   0% used [0x00000007f9680000, 0x00000007f9680000, 0x00000007f9790000)
  to   space 1088K,   0% used [0x00000007f9790000, 0x00000007f9790000, 0x00000007f98a0000)
 tenured generation   total 21888K, used 16K [0x00000007f98a0000, 0x00000007fae00000, 0x00000007fae00000)
   the space 21888K,   0% used [0x00000007f98a0000, 0x00000007f98a4010, 0x00000007f98a4200, 0x00000007fae00000)
 compacting perm gen  total 21248K, used 2635K [0x00000007fae00000, 0x00000007fc2c0000, 0x0000000800000000)
   the space 21248K,  12% used [0x00000007fae00000, 0x00000007fb092fc0, 0x00000007fb093000, 0x00000007fc2c0000)
 */
/* PretenureSizeThreshold = 1000 -XX:-UseTLAB 大对象直接在老年代分配
Heap
 def new generation   total 9792K, used 832K [0x00000007f8e00000, 0x00000007f98a0000, 0x00000007f98a0000)
  eden space 8704K,   9% used [0x00000007f8e00000, 0x00000007f8ed0048, 0x00000007f9680000)
  from space 1088K,   0% used [0x00000007f9680000, 0x00000007f9680000, 0x00000007f9790000)
  to   space 1088K,   0% used [0x00000007f9790000, 0x00000007f9790000, 0x00000007f98a0000)
 tenured generation   total 21888K, used 5398K [0x00000007f98a0000, 0x00000007fae00000, 0x00000007fae00000)
   the space 21888K,  24% used [0x00000007f98a0000, 0x00000007f9de58d0, 0x00000007f9de5a00, 0x00000007fae00000)
 compacting perm gen  total 21248K, used 2635K [0x00000007fae00000, 0x00000007fc2c0000, 0x0000000800000000)
   the space 21248K,  12% used [0x00000007fae00000, 0x00000007fb092fc0, 0x00000007fb093000, 0x00000007fc2c0000)
 */
}

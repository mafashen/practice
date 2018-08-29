package com.mafashen.jvm;

public class NewSizeDemo {

	/*
		-Xmx20m -Xms20m -Xmn1m -XX:SurvivorRatio=2 -XX:+PrintGCDetails -XX:+PrintGC -XX:+UseParNewGC
		第一次分配新生代空间不足触发youngGC
	 	[GC[ParNew: 512K->256K(768K), 0.0014540 secs] 512K->318K(20224K), 0.0014760 secs] [Times: user=0.00 sys=0.01, real=0.00 secs]
		Heap
		 par new generation   total 768K, used 490K [0x00000007f9a00000, 0x00000007f9b00000, 0x00000007f9b00000)
		  eden space 512K,  45% used [0x00000007f9a00000, 0x00000007f9a3aac0, 0x00000007f9a80000)
		  from space 256K, 100% used [0x00000007f9ac0000, 0x00000007f9b00000, 0x00000007f9b00000)
		  to   space 256K,   0% used [0x00000007f9a80000, 0x00000007f9a80000, 0x00000007f9ac0000)
分配担保	tenured generation   total 19456K, used 10302K [0x00000007f9b00000, 0x00000007fae00000, 0x00000007fae00000)
		   the space 19456K,  52% used [0x00000007f9b00000, 0x00000007fa50fa88, 0x00000007fa50fc00, 0x00000007fae00000)
		 compacting perm gen  total 21248K, used 2628K [0x00000007fae00000, 0x00000007fc2c0000, 0x0000000800000000)
		   the space 21248K,  12% used [0x00000007fae00000, 0x00000007fb091220, 0x00000007fb091400, 0x00000007fc2c0000)
	 */

	/*
		-Xmx20m -Xms20m -Xmn7m -XX:SurvivorRatio=2 -XX:+PrintGCDetails -XX:+PrintGC -XX:+UseParNewGC
		新生代7m空间,可用7*2/3=5376不足以容纳所有byte[] , 所有内存分配都在新生代进行,通过GC保证了新生代有足够空间
	 	[GC[ParNew: 2854K->1405K(5376K), 0.0012210 secs] 2854K->1405K(18688K), 0.0012480 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
		[GC[ParNew: 4559K->1056K(5376K), 0.0011410 secs] 4559K->1427K(18688K), 0.0011600 secs] [Times: user=0.01 sys=0.00, real=0.01 secs]
		[GC[ParNew: 4158K->1024K(5376K), 0.0008590 secs] 4528K->1410K(18688K), 0.0008790 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
		Heap
		 par new generation   total 5376K, used 3163K [0x00000007f9a00000, 0x00000007fa100000, 0x00000007fa100000)
		  eden space 3584K,  59% used [0x00000007f9a00000, 0x00000007f9c16ce8, 0x00000007f9d80000)
		  from space 1792K,  57% used [0x00000007f9f40000, 0x00000007fa040010, 0x00000007fa100000)
		  to   space 1792K,   0% used [0x00000007f9d80000, 0x00000007f9d80000, 0x00000007f9f40000)
	晋升	tenured generation   total 13312K, used 386K [0x00000007fa100000, 0x00000007fae00000, 0x00000007fae00000)
		   the space 13312K,   2% used [0x00000007fa100000, 0x00000007fa160918, 0x00000007fa160a00, 0x00000007fae00000)
		 compacting perm gen  total 21248K, used 2628K [0x00000007fae00000, 0x00000007fc2c0000, 0x0000000800000000)
		   the space 21248K,  12% used [0x00000007fae00000, 0x00000007fb091220, 0x00000007fb091400, 0x00000007fc2c0000)
	 */

	/*
		-Xmx20m -Xms20m -Xmn15m -XX:SurvivorRatio=8 -XX:+PrintGCDetails -XX:+PrintGC -XX:+UseParNewGC
		新生代15m空间,eden 15*8/10=12m足以容纳所有byte[] , 所有内存分配都在新生代eden上进行
	 	Heap
		 par new generation   total 13824K, used 11469K [0x00000007f9800000, 0x00000007fa700000, 0x00000007fa700000)
		  eden space 12288K,  93% used [0x00000007f9800000, 0x00000007fa333790, 0x00000007fa400000)
		  from space 1536K,   0% used [0x00000007fa400000, 0x00000007fa400000, 0x00000007fa580000)
		  to   space 1536K,   0% used [0x00000007fa580000, 0x00000007fa580000, 0x00000007fa700000)
		 tenured generation   total 5120K, used 0K [0x00000007fa700000, 0x00000007fac00000, 0x00000007fae00000)
		   the space 5120K,   0% used [0x00000007fa700000, 0x00000007fa700000, 0x00000007fa700200, 0x00000007fac00000)
		 compacting perm gen  total 21248K, used 2628K [0x00000007fae00000, 0x00000007fc2c0000, 0x0000000800000000)
		   the space 21248K,  12% used [0x00000007fae00000, 0x00000007fb091220, 0x00000007fb091400, 0x00000007fc2c0000)
	 */

	/*
		-Xmx20m -Xms20m -XX:NewRatio=2 -XX:+PrintGCDetails -XX:+PrintGC -XX:+UseParNewGC
		新生代20*1/3=6.6m 老年代13.4m
		[GC[ParNew: 5009K->377K(6144K), 0.0013500 secs] 5009K->1402K(19840K), 0.0013740 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
		[GC[ParNew: 5617K->32K(6144K), 0.0011460 secs] 6641K->2455K(19840K), 0.0011630 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
		Heap
		 par new generation   total 6144K, used 1111K [0x00000007f9a00000, 0x00000007fa0a0000, 0x00000007fa0a0000)
		  eden space 5504K,  19% used [0x00000007f9a00000, 0x00000007f9b0dca0, 0x00000007f9f60000)
		  from space 640K,   5% used [0x00000007f9f60000, 0x00000007f9f68128, 0x00000007fa000000)
		  to   space 640K,   0% used [0x00000007fa000000, 0x00000007fa000000, 0x00000007fa0a0000)
		  youngGC时,from/to区空间不足以容纳任何一个byte[] , 空间担保,所以直接进入老年代
		 tenured generation   total 13696K, used 2423K [0x00000007fa0a0000, 0x00000007fae00000, 0x00000007fae00000)
		   the space 13696K,  17% used [0x00000007fa0a0000, 0x00000007fa2fde58, 0x00000007fa2fe000, 0x00000007fae00000)
		 compacting perm gen  total 21248K, used 2628K [0x00000007fae00000, 0x00000007fc2c0000, 0x0000000800000000)
		   the space 21248K,  12% used [0x00000007fae00000, 0x00000007fb091220, 0x00000007fb091400, 0x00000007fc2c0000)
	 */
	public static void main(String[] args) {
		byte[] b = null;
		for (int i = 0; i < 10; i++) {
			b = new byte[1024 * 1024];
		}
	}
}

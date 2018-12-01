package com.mafashen.jvm.gc;

public class UseTLAB {

	public static void alloc(){
		byte[] b = new byte[2];
		b[0] = 1;
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100000000; i++) {
			alloc();
		}
		long end = System.currentTimeMillis();
		System.out.println("cast " + (end - start));
		/*
			use TLAB 	cast 695
			not use 	cast 1604
		 */
		/*
		desired_size : TLAB大小 , 通过 TLABSize 指定
		slow allocs : 从上一次YoungGC到现在为止慢分配次数,即由于TLAB空闲空间大小不足,而将对象直接在堆上分配
		refill waste : refill_waste值
		alloc :	当前线程的TLAB分配比例和使用评估量 , 这是一个统计数据,
			分配比例表示自上一次YoungGC后的number_of_refills * desired_size / used_tlab 的加权平均值
			使用评估量表示该平均值乘以used_tlab, 意为这个TLAB上大约合计被分配了多少空间
		refills : 表示该线程的TLAB空间被重新分配并填充的次数
		waste : 表示空间的浪费比例,浪费由后续的gc,slow,fast组成.
			gc : 表示当前YoungGC发生时,尚空闲的TLAB空间
			slow fast : 表示当TLAB被废弃时尚未使用的TLAB空间,fast表示这个refill操作是通过JIT编译优化的(禁用JIT,那么fast永远为0)
			waste比例 = 浪费空间总和(fast+slow+gc) / 总分配大小(_number_of_refills * _desired_size)

TLAB: gc thread: 0x00007fc6e881b000 [id: 14851] desired_size: 100KB slow allocs: 0  refill waste: 1024B alloc: 0.07570     5000KB refills: 1 waste 100.0% gc: 102400B slow: 0B fast: 0B
TLAB: gc thread: 0x00007fc6e8811000 [id: 14595] desired_size: 100KB slow allocs: 0  refill waste: 1024B alloc: 0.07570     5000KB refills: 1 waste 99.8% gc: 102216B slow: 0B fast: 0B
TLAB: gc thread: 0x00007fc6eb001000 [id: 19459] desired_size: 100KB slow allocs: 0  refill waste: 1024B alloc: 0.07570     5000KB refills: 1 waste 99.9% gc: 102264B slow: 0B fast: 0B
TLAB: gc thread: 0x00007fc6e8801800 [id: 6147] desired_size: 100KB slow allocs: 2  refill waste: 1024B alloc: 0.07570     5000KB refills: 658 waste  0.0% gc: 0B slow: 11296B fast: 0B
TLAB totals: thrds: 4  refills: 661 max: 658 slow allocs: 2 max 2 waste:  0.5% gc: 306880B max: 102400B slow: 11296B max: 11296B fast: 0B max: 0B
TLAB: gc thread: 0x00007fc6eb001000 [id: 19459] desired_size: 100KB slow allocs: 0  refill waste: 1024B alloc: 0.03861     2550KB refills: 1 waste 99.9% gc: 102288B slow: 0B fast: 0B
TLAB: gc thread: 0x00007fc6e880c800 [id: 12547] desired_size: 100KB slow allocs: 0  refill waste: 1024B alloc: 0.07570     5000KB refills: 1 waste 99.9% gc: 102336B slow: 0B fast: 0B
TLAB: gc thread: 0x00007fc6e8801800 [id: 6147] desired_size: 100KB slow allocs: 0  refill waste: 1024B alloc: 0.53597    35400KB refills: 659 waste  0.0% gc: 0B slow: 10552B fast: 0B
TLAB totals: thrds: 3  refills: 661 max: 659 slow allocs: 0 max 0 waste:  0.3% gc: 204624B max: 102336B slow: 10552B max: 10552B fast: 0B max: 0B
TLAB: gc thread: 0x00007fc6e8801800 [id: 6147] desired_size: 100KB slow allocs: 0  refill waste: 1024B alloc: 0.69760    46075KB refills: 661 waste  0.0% gc: 0B slow: 10584B fast: 0B
TLAB totals: thrds: 1  refills: 661 max: 661 slow allocs: 0 max 0 waste:  0.0% gc: 0B max: 0B slow: 10584B max: 10584B fast: 0B max: 0B
TLAB: gc thread: 0x00007fc6eb001000 [id: 19459] desired_size: 100KB slow allocs: 0  refill waste: 1024B alloc: 0.02563     1692KB refills: 1 waste 99.9% gc: 102320B slow: 0B fast: 0B
TLAB: gc thread: 0x00007fc6e8801800 [id: 6147] desired_size: 100KB slow allocs: 0  refill waste: 1024B alloc: 0.80371    53084KB refills: 660 waste  0.0% gc: 0B slow: 10568B fast: 0B
TLAB totals: thrds: 2  refills: 661 max: 660 slow allocs: 0 max 0 waste:  0.2% gc: 102320B max: 102320B slow: 10568B max: 10568B fast: 0B max: 0B
TLAB: gc thread: 0x00007fc6e8801800 [id: 6147] desired_size: 100KB slow allocs: 0  refill waste: 1024B alloc: 0.87216   115209KB refills: 1321 waste  0.0% gc: 0B slow: 21144B fast: 0B
TLAB totals: thrds: 1  refills: 1321 max: 1321 slow allocs: 0 max 0 waste:  0.0% gc: 0B max: 0B slow: 21144B max: 21144B fast: 0B max: 0B
TLAB: gc thread: 0x00007fc6e8801800 [id: 6147] desired_size: 100KB slow allocs: 0  refill waste: 1024B alloc: 0.91691   121121KB refills: 1321 waste  0.0% gc: 0B slow: 21144B fast: 0B
TLAB totals: thrds: 1  refills: 1321 max: 1321 slow allocs: 0 max 0 waste:  0.0% gc: 0B max: 0B slow: 21144B max: 21144B fast: 0B max: 0B
TLAB: gc thread: 0x00007fc6e8801800 [id: 6147] desired_size: 100KB slow allocs: 0  refill waste: 1024B alloc: 0.94601   249927KB refills: 2642 waste  0.0% gc: 0B slow: 42288B fast: 0B
TLAB totals: thrds: 1  refills: 2642 max: 2642 slow allocs: 0 max 0 waste:  0.0% gc: 0B max: 0B slow: 42288B max: 42288B fast: 0B max: 0B
TLAB: gc thread: 0x00007fc6e8801800 [id: 6147] desired_size: 100KB slow allocs: 0  refill waste: 1024B alloc: 0.96491   254923KB refills: 2642 waste  0.0% gc: 0B slow: 42288B fast: 0B
TLAB totals: thrds: 1  refills: 2642 max: 2642 slow allocs: 0 max 0 waste:  0.0% gc: 0B max: 0B slow: 42288B max: 42288B fast: 0B max: 0B
TLAB: gc thread: 0x00007fc6e8801800 [id: 6147] desired_size: 100KB slow allocs: 0  refill waste: 1024B alloc: 0.97720   516339KB refills: 5283 waste  0.0% gc: 0B slow: 84528B fast: 0B
TLAB totals: thrds: 1  refills: 5283 max: 5283 slow allocs: 0 max 0 waste:  0.0% gc: 0B max: 0B slow: 84528B max: 84528B fast: 0B max: 0B
TLAB: gc thread: 0x00007fc6e8801800 [id: 6147] desired_size: 100KB slow allocs: 0  refill waste: 1024B alloc: 0.98513   520526KB refills: 5283 waste  0.0% gc: 0B slow: 84528B fast: 0B

	totals : 显示了所有线程的统计情况
	thrds : 相关线程总数
	refills : 所有线程refills总数
	max : refills次数最多的线程的refills次数

TLAB totals: thrds: 1  refills: 5283 max: 5283 slow allocs: 0 max 0 waste:  0.0% gc: 0B max: 0B slow: 84528B max: 84528B fast: 0B max: 0B
cast 773
		 */
	}
}

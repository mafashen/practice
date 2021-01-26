package com.mafashen.structure.arithmetic;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * 布隆过滤器
 * @author mafashen
 * created on 2019/4/2.
 */
public class BoolFilter {
	private byte[] filter ;
	private int num;
	private int hashCnt = 4;

	public BoolFilter(int num) {
		this.num = num;
		this.filter = new byte[num];
	}

	public boolean put(int key){
		if (key > num){
			return false;
		}
		for (int i = 0; i < hashCnt; i++) {
			int hash = hash0(key, i);
			if (filter[hash] == 1){
				return false;
			}
			filter[hash] = 1;
		}
		return true;
	}

	//hash后平均落在 hashCnt 个区间
	private int hash0(int key , int step){
		int section = num / hashCnt  ;
		int hash = key % section ;
		return hash + step * section ;
	}

	private static int total = 1000000;
	private static BloomFilter<Integer> bf = BloomFilter.create(Funnels.integerFunnel(), total, 0.0001);


	public static void main(String[] args) {
		BoolFilter filter = new BoolFilter(12);
		System.out.println(filter.put(1));
		System.out.println(filter.put(2));
		System.out.println(filter.put(3));
		System.out.println(filter.put(4));
		System.out.println(filter.put(4));

		// 初始化1000000条数据到过滤器中
		for (int i = 0; i < total; i++) {
			bf.put(i);
		}

		// 匹配已在过滤器中的值，是否有匹配不上的
		for (int i = 0; i < total; i++) {
			if (!bf.mightContain(i)) {
				System.out.println("有坏人逃脱了~~~");
			}
		}

		// 匹配不在过滤器中的10000个值，有多少匹配出来
		int count = 0;
		for (int i = total; i < total + 10000; i++) {
			if (bf.mightContain(i)) {
				count++;
			}
		}
		System.out.println("误伤的数量：" + count);
	}
}

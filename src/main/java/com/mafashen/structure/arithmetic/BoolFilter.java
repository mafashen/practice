package com.mafashen.structure.arithmetic;

/**
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

	public static void main(String[] args) {
		BoolFilter filter = new BoolFilter(12);
		System.out.println(filter.put(1));
		System.out.println(filter.put(2));
		System.out.println(filter.put(3));
		System.out.println(filter.put(4));
		System.out.println(filter.put(4));
	}
}

package com.mafashen.structure.arithmetic;

/**
 * 回溯算法解0-1背包问题
 * @author mafashen
 * created on 2019/3/20.
 */
public class _0_1_Bag {

	public static int MAX_WEIGHT = 0;

	public static void bag(int i, int cw, int[] goods, int n, int w){
		//已经装完了, 或者超过背包承重
		if (i == n || cw >= w){
			//更新历史最大重量
			if (cw > MAX_WEIGHT){
				MAX_WEIGHT = cw;
			}
			return;
		}

		//是否把第i个物品放入背包
		bag(i+1, cw, goods, n, w);	//不放
		//截枝,减少递归次数
		if (cw + goods[i] <= w){
			bag(i+1, cw+goods[i], goods, n, w); //第i个物品放入背包
		}
	}

	public static void main(String[] args) {
		int[] goods = {4,5,6,7,16,11};
		bag(0, 0, goods, 6, 19);
		System.out.println(MAX_WEIGHT);
	}
}

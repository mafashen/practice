package com.mafashen.structure.arithmetic;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import lombok.Data;

/**
 * 动态规划
 * @author mafashen
 * created on 2019/3/22.
 */
public class DynamicPlanning {

	public static void yanghuisanjiao(int[] arr, int n, int m){
		boolean[][] ret = new boolean[n][m+1];

	}

	//状态转移方程法:f(n) = 1 + min(f(n-moneyItems[i]))
	static int mem[] = new int[200];
	public static int moneyChange(List<Integer> items, int total){
		if (items.contains(total)){
			mem[total] = 1;
			return 1;
		}
		if (mem[total] > 0){
			return mem[total];
		}
		int[] mins = new int[items.size()];
		for (int i = 0; i < items.size(); i++) {
			if (total - items.get(i) > 0){
				mins[i] = moneyChange(items, total - items.get(i)) + 1;
			}
		}
		int min = mins[0];
		for (int i : mins) {
			if (i > 0 && i < min){
				min = i;
			}
		}
		mem[total] = min;
		return min;
	}

	//c[i,j] = min(c[i-1,j] , c[i,j-coins[i]]+1)
	//c[i-1,j] : 不使用第i枚硬币时,找零j的最小值
	//c[i,j-coins[i]]+1 : 使用第i枚硬币找零时,对 j-coins[i]金额找零的最小值 + 1
	//动态规划理解:基于每一个子集问题的不同选择,结合最优解的判定,减少选择的次数
	public static int moneyChange2(int[] coins, int w){
		int length = coins.length;
		int[][] min = new int[length+1][w+1];

		for (int i = 0; i < coins.length; i++) {
			min[i][0] = 0;
		}
		for (int i = 0; i <= w; i++) {
			min[0][i] = Integer.MAX_VALUE;
		}

		for (int money = 1; money <= w; money++) {
			for (int kind = 1; kind <= length; kind++) {
				if (coins[kind-1] > money ){
					min[kind][money] = min[kind-1][money];//不能使用j找零
					continue;
				}
				min[kind][money] = Math.min(min[kind-1][money] , min[kind][money-coins[kind-1]]+1);
			}
		}

		//反推选择
		for (int money = w; money > 0 ; ) {
			for (int kind = length-1; kind >= 0 ; kind--) {
				if (min[kind+1][money] != min[kind][money] || coins[kind] == money){
					System.out.println("选择了" + coins[kind]);
					money -= coins[kind];
					break;
				}
			}
		}
		return min[length][w];
	}

	//贪心算法,每次选择最大的能满足条件的(小于w)的进行找零
	public static int moneyChange3(int[] items, int w){
		int[] ret = new int[w];
		int j = 0;
		while(w > 0){
			for (int i = items.length - 1; i >= 0; i--) {
				if (items[i] <= w){
					w -= items[i];
					System.out.println("选择"+items[i]);
					ret[j++] = items[i];
					break;
				}
			}
		}
		return ret.length;
	}

	//网上的实现
	public static int countMoneyMin(int[] moneyItems, int resultMemory) {
		if (null == moneyItems || moneyItems.length < 1) {
			return -1;
		}
		if (resultMemory < 1) {
			return -1;
		}
		// 计算遍历的层数，此按最小金额来支付即为最大层数
		int levelNum = resultMemory / moneyItems[0];
		int length = moneyItems.length;
		int[][] status = new int[levelNum][resultMemory + 1];
		// 初始化状态数组
		for (int i = 0; i < levelNum; i++) {
			for (int j = 0; j < resultMemory + 1; j++) {
				status[i][j] = -1;
			}
		}
		// 将第一层的数数据填充
		for (int i = 0; i < length; i++) {
			status[0][moneyItems[i]] = moneyItems[i];
		}
		int minNum = -1;
		// 计算推导状态
		for (int i = 1; i < levelNum; i++) {
			// 推导出当前状态
			for (int j = 0; j < resultMemory; j++) {
				if (status[i - 1][j] != -1) {
					// 遍历元素,进行累加
					for (int k = 0; k < length; k++) {
						if (j + moneyItems[k] <= resultMemory) {
							status[i][j + moneyItems[k]] = moneyItems[k];
						}
					}
				}
				// 找到最小的张数
				if (status[i][resultMemory] >= 0) {
					minNum = i + 1;
					break;
				}
			}
			if (minNum > 0) {
				break;
			}
		}
		int befValue = resultMemory;
		// 进行反推出，币的组合
		for (int i = minNum - 1; i >= 0; i--) {
			for (int j = resultMemory; j >= 0; j--) {
				if (j == befValue) {
					System.out.println("当前的为:" + status[i][j]);
					befValue = befValue - status[i][j];
					break;
				}
			}
		}
		return minNum;
	}

	//莱文斯坦距离问题
	//m[i][j] = min(m[i-1][j-1], m[i-1][j]+1, m[i][j-1]+1) (a[i]=b[j])
	//			min(m[i-1][j-1]+1, m[i-1][j]+1, m[i][j-1]+1) (a[i]!=b[j])
	public static int LevenshteinDistance(char[] a, int n, char[] b, int m){
		int[][] minDist = new int[n][m];

		//初始化边界
		for (int i = 0; i < m; i++) {
			if (a[0] == b[i]){
				minDist[0][i] = i;
			}else if(i > 0){
				minDist[0][i] = minDist[0][i-1]+1;
			}else{
				minDist[0][i] = 1;
			}
		}
		for (int i = 0; i < n; i++) {
			if (a[i] == b[i]){
				minDist[i][0] = i;
			}else if (i > 0){
				minDist[i][0] = minDist[i-1][0] + 1;
			}else{
				minDist[i][0] = 1;
			}
		}

		for (int i = 1; i < n; i++) {
			for (int j = 1; j < m; j++) {
				if (a[i] == b[j]){
					minDist[i][j] = min(minDist[i-1][j-1], minDist[i-1][j]+1, minDist[i][j-1]+1);
				}else{
					minDist[i][j] = min(minDist[i-1][j-1]+1, minDist[i-1][j]+1, minDist[i][j-1]+1);;
				}
			}
		}
		System.out.println(minDist[n-1][m-1]);
		return minDist[n-1][m-1];
	}

	public static int min(int ... arr){
		int min = arr[0];
		for (int i : arr) {
			if (min > i){
				min = i;
			}
		}
		return min;
	}

	public static void main(String[] args) {
//		System.out.println(moneyChange(Arrays.asList(1, 2, 5, 10,20,50,100), 103));
//		countMoneyMin(new int[]{1, 2, 5, 10, 20, 50, 100}, 103);
//		moneyChange2(new int[]{1, 2, 5, 10, 20, 50, 100}, 68);
//		moneyChange3(new int[]{1, 2, 5, 10, 20, 50, 100}, 68);
		LevenshteinDistance("mitcm".toCharArray(), 5, "mtacnu".toCharArray(), 6);
	}
}

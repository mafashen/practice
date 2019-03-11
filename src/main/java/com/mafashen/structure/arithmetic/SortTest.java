package com.mafashen.structure.arithmetic;

/**
 * @author mafashen
 * created on 2019/2/26.
 */
public class SortTest {

	/**
	 * 冒泡排序
	 * @param arr
	 * @param n
	 */
	public static void bubbleSort(int arr[] , int n){
		for (int i = 0; i < n; i++) {
			boolean sweep = false;
			for (int j = 0; j < n-i-1; j++) {
				if (arr[j] > arr[j+1]){
					int temp = arr[j];
					arr[j] = arr[j+1];
					arr[j+1] = temp;
					sweep = true;
				}

				if (!sweep){
					break;
				}
			}
		}
	}

	/**
	 * 选择排序
	 * @param arr
	 * @param n
	 */
	public static void selectSort(int arr[] , int n){
		for (int i = 0; i < n; i++) {
			int min = arr[i];
			int k = i;
			for (int j = i+1; j < n-i; j++) {
				if (arr[j] < min){
					min = arr[j];
					k = j;
				}
			}
			arr[k] = arr[i];
			arr[i] = min;
		}
	}

	/**
	 * 插入排序
	 * @param arr
	 * @param n
	 */
	public static void insertSort(int arr[] , int n){
		for (int i = 1; i < n; i++) {
			int value = arr[i]; //未排序区第一个数
			int j = i -1;
			//遍历已排序区,插入到合适位置,之后的向后移动一位
			for (; j >= 0; j--) {
				if (arr[j] > value){
					arr[j+1] = arr[j];
				}else{
					break;
				}
			}
			arr[j+1] = value;
		}
	}

	/**
	 * 归并排序
	 * @param arr
	 * @param n
	 */
	public static void mergeSort(int arr[] , int n){
		mergeSort0(arr, 0, n-1);
	}

	private static void mergeSort0(int arr[], int start, int end){
		if (start >= end)
			return;
		int mid = start + (end - start) / 2;
		mergeSort0(arr, start, mid);
		mergeSort0(arr, mid+1, end);
		merge(arr, start, mid, end);
	}

	/**
	 * 合并排序后两个分区
	 * @param arr
	 * @param start
	 * @param mid
	 * @param end
	 */
	private static void merge(int[] arr, int start, int mid, int end) {
		int[] temp = new int[arr.length];
		int i= start, j = mid+1, k = 0;
		while(i <= mid && j <= end){
			if(arr[i] <= arr[j]){
				temp[k++] = arr[i++];
			}else{
				temp[k++] = arr[j++];
			}
		}

		int left = i, right = mid;
		if (j <= end){
			left = j; right = end;
		}
		for (;left <= right; left++){
			temp[k++] = arr[left];
		}
		for (int l = 0; l <= end - start; l++) {
			arr[start+l] = temp[l];
		}
	}


	/**
	 * 快速排序
	 * @param arr
	 * @param n
	 */
	public static void quickSort(int[] arr, int n){
		quickSort0(arr, 0, n-1);
	}

	private static void quickSort0(int[] arr, int p, int r) {
		if(p >= r)
			return;
		int q = partition(arr, p, r);
		quickSort0(arr, p, q-1);
		quickSort0(arr, p+1, r);
	}

	/**
	 * 按分区点将arr分为大于与小于pivot的两部分
	 * @param arr
	 * @param p
	 * @param r
	 * @return 分区点
	 */
	private static int partition(int[] arr, int p, int r) {
		int pivot = arr[r]; //分区点
		int i = p;			//i记录大于pivot的下标
		for (int j=p; j < r; j++){
			if (arr[j] < pivot){
				if(i == j)
					i++;
				else{
					int temp = arr[i];
					arr[i++] = arr[j];
					arr[j] = temp;
				}
			}
		}

		int temp = arr[i];
		arr[i] = arr[r];
		arr[r] = temp;

		System.out.println("pivot = " + i);
		return i;
	}

	/**
	 * 桶排序
	 * @param arr
	 * @param n
	 */
	public static void bucketSort(int[] arr, int n){
		bucketSort0(arr, 5);
	}

	private static void bucketSort0(int[] arr, int b){
		int min = min(arr);
		int max = max(arr);
		int step = (max - min) / b ;

		int[][] buckets = new int[b][step];
		int[] subscripts = new int[b];

		for (int i = 0; i < arr.length; i++) {
			buckets[(arr[i] - min) / step -1 ][subscripts[(arr[i] - min) / step ]++] = arr[i];
		}
		int k = 0;
		for (int[] bucket : buckets) {
			for (int i : bucket) {
				arr[k++] = i;
			}
		}
	}

	/**
	 * 计数排序,适用于数据相差范围不大情况
	 * 	桶排序的一种特例
	 * @param arr
	 * @param n
	 */
	public static void rankSort(int[] arr, int n){
		int max = max(arr);
		int[] buckets = new int[max + 1];
		//对应数值出现的次数+1
		for (int i : arr) {
			buckets[i] ++;
		}
		//累加小于等于当前数值的数出现的次数
		for (int i = 1; i < buckets.length; i++) {
			buckets[i] += buckets[i-1];
		}
		int[] tmp = new int[n];
		//倒序遍历arr数组,从buckets对应下标对应的值中知道小于等于当前数值的数出现的位置,确定当前数排序后的位置buckets[arr[i]]
		for (int i = n-1; i >= 0 ; i--) {
			tmp[buckets[arr[i]]-1] = arr[i];
			buckets[arr[i]]--;
		}

		for (int i = 0; i < n; i++) {
			arr[i] = tmp[i];
		}
	}

	private static int min(int[] arr){
		int min = arr[0];
		for (int i : arr) {
			if (i < min){
				min = i;
			}
		}
		return min;
	}

	private static int max(int[] arr){
		int max = arr[0];
		for (int i : arr) {
			if (i > max){
				max = i;
			}
		}
		return max;
	}

	public static void main(String[] args) {
		int arr[] = new int[]{8,5,3,6,9,4,7,1,2,7,9,20};
		rankSort(arr, arr.length);
		for (int i : arr) {
			System.out.print(i);
		}
	}
}

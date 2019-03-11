package com.mafashen.java.arithmetic;

/**
 * @author mafashen
 * created on 2019/3/1.
 */
public class SearchTest {

	/**
	 * 二分查找
	 * @param arr	待查找数组,必须是有序的
	 * @param value
	 */
	public static int binarySearch(int arr[], int value){
		int low = 0;
		int high = arr.length - 1;
		int k = 0;
		while (low <= high){
			k++;
			int mid = low+((high-low)>>1); //low+(high-low)/2  //(low + high) / 2 可能溢出
			if (value == arr[mid]){
				System.out.println("takes turns : " + k);
				return mid;
			}else if(value < arr[mid]){
				high = mid - 1;	//high = 3 , low = 3 , arr[3] != value => deadLoop
			}else{
				low = mid + 1;
			}
		}
		return -1;
	}

	public static int recursionBinarySearch(int[] a, int low, int high, int value){
		if (low > high)
			return -1;
		int mid = low + ((high-low) >> 1);
		if (a[mid] == value) {
			return mid;
		} else if (a[mid] > value) {
			return recursionBinarySearch(a, low, mid - 1, value);
		} else {
			return recursionBinarySearch(a, mid + 1, high, value);
		}
	}

	/**
	 * 二分查找 返回第一个符合的下标
	 */
	public static int binarySearchFor1stMatch(int arr[] , int value){
		int low = 0;
		int high = arr.length - 1;
		int turns = 0;
		while (low <= high){
			int mid = low + (high - low) / 2;
			if (arr[mid] >= value){		//等于value的时候也继续往前找,前面的数 <= value
				high = mid - 1;
			}else{
				low = mid + 1;
			}
			turns ++;
		}
		System.out.println("binarySearchFor1stMatch takes turns: " + turns);
		if (arr[low] == value)
			return low;
		return -1;
	}

	//易于理解的另一种写法
	public static int binarySearchFor1stMatch1(int arr[] , int value){
		int low = 0;
		int high = arr.length - 1;
		int turns = 0;
		while (low <= high){
			int mid = low + (high - low) / 2;
			if (arr[mid] > value){		//等于value的时候也继续往前找,前面的数 <= value
				high = mid - 1;
			}else if(arr[mid] < value){
				low = mid + 1;
			}else{
				if (mid == 0 || arr[mid-1] != value){
					return mid;
				}else{
					high = mid - 1;	//前面还可能存在 == value的值
				}
			}
			turns ++;
		}
		System.out.println("binarySearchFor1stMatch1 takes turns: " + turns);
		return -1;
	}

	/**
	 *二分查找最后一个匹配
	 */
	public static int binarySearchForLastMatch(int arr[] , int value){
		int low = 0;
		int high = arr.length - 1;
		int turns = 0;
		while (low <= high){
			int mid = low + (high - low) / 2;
			if (arr[mid] <= value){		//等于value的时候也继续往前找,前面的数 <= value
				low = mid + 1;
			}else{
				high = mid - 1;
			}
			turns ++;
		}
		System.out.println("binarySearchForLastMatch takes turns: " + turns);
		if (arr[high] == value)
			return low;
		return -1;
	}

	/**
	 *二分查找第一个大于等于给定值的数
	 */
	public static int binarySearchForFirstLarge(int arr[] , int value){
		int low = 0;
		int high = arr.length - 1;
		int turns = 0;
		while (low <= high){
			int mid = low + (high - low) / 2;
			if (arr[mid] >= value){
				if (mid == 0 || arr[mid-1] < value)		//arr[mid-1] < value && arr[mid] >= value mid即是第一个>=的值
					return mid;
				high = mid - 1;
			}else{
				low = mid + 1;
			}
			turns ++;
		}
		System.out.println("binarySearchForLastMatch takes turns: " + turns);
		return -1;
	}

	/**
	 *二分查找最后一个小于等于给定值
	 */
	public static int binarySearchForLastLess(int arr[] , int value){
		int low = 0;
		int high = arr.length - 1;
		int turns = 0;
		while (low <= high){
			int mid = low + (high - low) / 2;
			if (arr[mid] <= value){
				if (mid == 0 || arr[mid+1] > value)		//arr[mid+1] > value && arr[mid] <= value mid即是最后一个<=的值
					return mid;
				low = mid + 1;
			}else{
				high = mid - 1;
			}
			turns ++;
		}
		System.out.println("binarySearchForLastMatch takes turns: " + turns);
		return -1;
	}

	/**
	 * 开根运算
	 * @param n
	 * @return
	 */
	public static double square(int n){
		double low = 1;
		double high = n / 2;
		double square = low + (high - low) / 2;
		int loop = 0;
		while(low <= high && loop ++ < 10){
			if (square * square == n){
				break;
			}else if (square * square > n){
				high = square;
			}else{
				low = square;
			}
			square = low + (high - low) / 2;
		}
		return square;
	}

	public static void main(String[] args) {
		int arr[] = new int[]{1,2,3,4,5,6,7,8,9,10};
//		System.out.println(recursionBinarySearch(arr, 0, arr.length, 4));

//		System.out.println(square(500000));

		arr = new int[]{1,3,4,5,6,8,8,8,11,18};
		System.out.println(binarySearchFor1stMatch(arr, 8));
		System.out.println(binarySearchForLastMatch(arr, 8));
		System.out.println(binarySearch(arr, 8));
	}
}

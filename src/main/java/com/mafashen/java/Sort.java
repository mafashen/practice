package com.mafashen.java;

/**
 * @author mafashen
 * @since 2021-01-18.
 */
public class Sort {

    /**
     * 冒泡排序 时间复杂度 O(n2) 原地排序 稳定
     * @param arr
     */
    public static void bubbleSort(int arr[]){
        for (int i = 0, len = arr.length; i < len; i++) {
            boolean sweep = false;
            for (int j = i+1; j <= len - 1; j++) {
                if (arr[j] < arr[i]){
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                    sweep = true;
                }
                if (!sweep){
                    break;
                }
            }
        }
    }

    /**
     * 插入排序 时间复杂度 O(n2) 原地排序 稳定
     * @param arr
     */
    public static void insertSort(int arr[]){
        for (int i = 1, len = arr.length; i < len; i++) {
            int unSort = arr[i];
            int j = i - 1;
            for (; j >= 0; j--) {
                if (arr[j] > unSort){
                    arr[j + 1] = arr[j];
                }else{
                    break;
                }
            }
            arr[j+1] = unSort;
        }
    }

    /**
     * 选择排序 时间复杂度 O(n2) 原地排序 不稳定
     * @param arr
     */
    public static void selectSort(int arr[]){
        for (int i = 0, len = arr.length; i < len; i++) {
            int min = arr[i];
            int minIdx = i;
            for (int j = i + 1; j < len; j++) {
                if (arr[j] < min){
                    min = arr[j];
                    minIdx = j;
                }
            }
            if (minIdx != i){
                arr[minIdx] = arr[i];
                arr[i] = min;
            }
        }
    }

    /**
     * 归并排序 时间复杂度 O(n * log n) 非原地排序 稳定
     * @param arr
     */
    public static void mergeSort(int arr[]){
        mergeSort0(arr, 0, arr.length - 1);
    }

    private static void mergeSort0(int[] arr, int start, int end) {
        if (start >= end){
            return;
        }
        int mid = start + (end - start) / 2;
        mergeSort0(arr, start, mid);
        mergeSort0(arr, mid+1, end);
        merge(arr, start, mid, end);
    }

    private static void merge(int[] arr, int start, int mid, int end) {
        int[] temp = new int[arr.length];
        int i = start, j = mid+1, idx = 0;
        while(i <= mid && j <= end){
            if (arr[i] <= arr[j]){
                temp[idx++] = arr[i++];
            }else{
                temp[idx++] = arr[j++];
            }
        }
        //剩余数据处理
        if (i <= mid){
            for (int k = i; k <= mid; k++) {
                temp[idx++] = arr[k];
            }
        }
        if (j <= end){
            for (int k = j; k <= end; k++) {
                temp[idx++] = arr[k];
            }
        }

        // 拷贝回原数组
        for (int k = 0; k <= end - start; k++) {
            arr[start + k] = temp[k];
        }
    }

    /**
     * 快速排序 时间复杂度 O(n * log n) 非原地排序 不稳定
     * @param arr
     */
    public static void quickSort(int arr[]){
        quickSort0(arr, 0, arr.length - 1);
    }

    private static void quickSort0(int[] arr, int start, int end) {
        if (start >= end)
            return;
        int mid = partition(arr, start, end);
        quickSort0(arr, start, mid-1);
        quickSort0(arr, mid+1, end);
    }

    private static int partition(int[] arr, int start, int end) {
        int pivot = arr[end];
        int sortIdx = start; // 已处理区间(<pivot)
        for (int i = start; i < end; i++) {
            if (arr[i] < pivot){
                int temp = arr[sortIdx];
                arr[sortIdx] = arr[i];
                arr[i] = temp;
                sortIdx++;
            }
        }

        int temp = arr[sortIdx];
        arr[sortIdx] = arr[end];
        arr[end] = temp;
        return sortIdx;
    }

    public static void main(String[] args) {
        int arr[] = new int[]{9, 7, 2, 3, 5, 1, 4, 6, 8};

        quick(arr, 0, arr.length - 1);
        for (int i : arr) {
            System.out.printf("%d ", i);
        }
    }

    public static void quick(int[] arr, int s, int e){
        if(s >= e){
            return;
        }
        int m = part(arr, s, e);
        quick(arr, s, m-1);
        quick(arr, m+1, e);
    }

    public static int part(int[] arr, int s, int e){
        int pivot = arr[e];
        int i = s;
        for (int j = s; j < e; j++) {
            if (arr[j] < pivot){
                int temp = arr[i];
                arr[i++] = arr[j];
                arr[j] = temp;
            }
        }

        arr[e] = arr[i];
        arr[i] = pivot;
        return i;
    }
}

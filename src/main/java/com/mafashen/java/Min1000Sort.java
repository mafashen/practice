package com.mafashen.java;

import java.util.Arrays;
import java.util.Random;

public class Min1000Sort {
	int length;
	Integer[] arr;

	public Min1000Sort(){}

	public Min1000Sort(int length) {
		this.length = length;
		this.arr = new Integer[length];
	}

	public void add(int i){
		int index = i % length;
		if (arr[index] == null || arr[index] > i){
			arr[index] = i;
		}
	}

	@Override
	public String toString() {
		Arrays.sort(arr);
		return arr.toString();
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		Random random = new Random(50000000);
		int[] array = new int[5000000];
		for (int i = 0; i < 5000000; i++) {
			array[i] = Math.abs(random.nextInt());
		}

		Min1000Sort set = new Min1000Sort(1000);
		for (int i = 0; i < array.length; i++) {
			set.add(array[i]);
		}
		Arrays.sort(set.arr);
		for (Integer i : set.arr) {
			System.out.print(i + " ");
		}
		System.out.println();
		long end = System.currentTimeMillis();
		System.out.println("takes " + (end - start) + "ms");
	}
}

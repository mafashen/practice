package com.mafashen.jvm.code;

import org.apache.commons.lang3.RandomUtils;

/**
 * @author mafashen
 * created on 2019/4/17.
 */
public class CirculationOptimizeTest {

	public static void main(String[] args) {
		int size = RandomUtils.nextInt();
		for (int i = 0; i < size / 20; i++) {
			System.out.println(i);
		}
	}
}

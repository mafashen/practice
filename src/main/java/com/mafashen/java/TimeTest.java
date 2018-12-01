package com.mafashen.java;

import java.util.Date;

import org.junit.Test;

/**
 * @author mafashen 2018-10-30
 */
public class TimeTest {

	@Test
	public void testMill2Date(){
		long mill = 1541433600000L;
		System.out.println(new Date(mill));
	}

}

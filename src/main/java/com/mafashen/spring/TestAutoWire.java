package com.mafashen.spring;

import org.springframework.beans.factory.annotation.Autowired;

import com.mafashen.sdg.Cast;

public class TestAutoWire {

	@Autowired
	Cast cast;

	TestAutoWire(){
		System.out.println("init...");
		System.out.println(cast == null);
	}

	public static void main(String[] args){
		TestAutoWire t = new TestAutoWire();
		System.out.println(t.cast == null);
	}
}

package com.mafashen.jvm;

import java.util.HashMap;

public class Client {

	public static void main(String[] args) {
		useFather();
		useSon();
	}
	
	private static void useFather() {
		Father father = new Father();
		HashMap map = new HashMap<>();
		father.doSomething(map);
	}
	
	private static void useSon() {
		Son son = new Son();
		HashMap map = new HashMap<>();
		son.doSomething(map);
	}
}

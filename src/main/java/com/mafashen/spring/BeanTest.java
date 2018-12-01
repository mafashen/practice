package com.mafashen.spring;

public class BeanTest {
	private String testStr ;

	public BeanTest(){
	}

	public BeanTest(String testStr) {
		this.testStr = testStr;
	}

	public String getTestStr() {
		return testStr;
	}

	public void setTestStr(String testStr) {
		this.testStr = testStr;
	}
}

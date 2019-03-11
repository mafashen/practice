package com.mafashen.spring.aop;

import org.springframework.stereotype.Component;

/**
 * @author mafashen
 * created on 2018/12/3.
 */
//@Component
public class BizBean {

	private String msg ;

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void test(){
		System.out.println(msg);
	}
}

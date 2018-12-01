package com.mafashen.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author mafashen
 * created on 2018/11/25.
 */
public class ApplicationContextTest {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
		Object testBean = ctx.getBean("testBean");
	}
}

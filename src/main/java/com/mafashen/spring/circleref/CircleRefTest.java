package com.mafashen.spring.circleref;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author mafashen
 * created on 2018/11/8.
 */
public class CircleRefTest {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-context.xml");
		ctx.getBean("circleRefA");
	}

}

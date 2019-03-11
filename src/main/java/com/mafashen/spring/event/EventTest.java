package com.mafashen.spring.event;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author mafashen
 * created on 2018/12/2.
 */
public class EventTest {

	public static void main(String[] args) {
		ApplicationContext atx = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
		TestEvent event = new TestEvent("hello", "msg");
		atx.publishEvent(event);
	}
}

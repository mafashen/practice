package com.mafashen.spring.factory.method;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mafashen.spring.BeanTest;

/**
 * @author mafashen
 * created on 2018/11/12.
 */
public class FactoryMethodTest {

	public static BeanTest factoryMethod(){
		return new BeanTest("factory-method");
	}

	public static BeanTest factoryMethod(String value){
		return new BeanTest(value);
	}

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-context.xml");
		ctx.getBean("testBean");
	}
}

package com.mafashen.spring;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import com.mafashen.java.ClassTest;

public class XmlBeanFactoryTest {

	public static void main(String[] args) {
		BeanFactory fc = new XmlBeanFactory(new ClassPathResource("spring-context.xml"));
		BeanTest bean = fc.getBean(BeanTest.class);
		System.out.println(bean.getTestStr());
	}
}

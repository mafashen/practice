package com.mafashen.spring;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import com.mafashen.java.ClassTest;
import com.mafashen.spring.custom.editor.DateTypeDITest;

public class XmlBeanFactoryTest {

	public static void main(String[] args) {
		BeanFactory fc = new XmlBeanFactory(new ClassPathResource("spring-context.xml"));
		DateTypeDITest bean = fc.getBean(DateTypeDITest.class);
		System.out.println(bean.getDate());
	}
}

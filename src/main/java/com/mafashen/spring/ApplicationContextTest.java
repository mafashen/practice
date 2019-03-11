package com.mafashen.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mafashen.spring.aop.AspectJTest;
import com.mafashen.spring.aop.BizBean;
import com.mafashen.spring.custom.editor.DateTypeDITest;

/**
 * @author mafashen
 * created on 2018/11/25.
 */
public class ApplicationContextTest {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
//		DateTypeDITest bean = ctx.getBean(DateTypeDITest.class);
//		System.out.println(bean.getDate());

//		BizBean bizBean = ctx.getBean(BizBean.class);
//		bizBean.test();

		AspectJTest aspectjBean = ctx.getBean("aspectjBean", AspectJTest.class);
		aspectjBean.test();
	}
}

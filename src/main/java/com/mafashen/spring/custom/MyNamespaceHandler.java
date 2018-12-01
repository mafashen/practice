package com.mafashen.spring.custom;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author mafashen
 * created on 2018/10/30.
 */
public class MyNamespaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		registerBeanDefinitionParser("user", new UserBeanDefinitionParser());
	}
}

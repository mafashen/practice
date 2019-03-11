package com.mafashen.spring.custom.definition;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * @author mafashen
 * created on 2018/10/30.
 */
public class UserBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

	@Override
	protected Class<?> getBeanClass(Element element) {
		return User.class;//Element对应的类
	}

	@Override	//从Element中解析并提取对应的元素
	protected void doParse(Element element, BeanDefinitionBuilder builder) {
		String username = element.getAttribute("username");
		String email = element.getAttribute("email");

		//将提取的数据放入到BeanDefinitionBuilder,待完成所有bean的解析后统一注册到BeanFactory中
		if (StringUtils.hasText(username)){
			builder.addPropertyValue("username", username);
		}
		if (StringUtils.hasText(email)){
			builder.addPropertyValue("email", email);
		}
	}
}

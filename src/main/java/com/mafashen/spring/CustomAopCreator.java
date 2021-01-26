package com.mafashen.spring;

import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;

/**
 * @author mafashen
 * @since 2019-11-18.
 */
//@Component("org.springframework.aop.config.internalAutoProxyCreator")
public class CustomAopCreator extends AnnotationAwareAspectJAutoProxyCreator {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        System.out.println("CustomAopCreator");
        return super.postProcessBeforeInstantiation(beanClass, beanName);
    }
}

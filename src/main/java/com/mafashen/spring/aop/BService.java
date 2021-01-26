package com.mafashen.spring.aop;

import org.springframework.aop.framework.AopContext;
import org.springframework.aop.framework.AopProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * @author mafashen
 * @since 2021-01-04.
 */
@Lazy
@Service
public class BService {

    @Lazy
    @Autowired
    private AService aService;

    public void doSomeThing(){
        System.out.println("B doSomeThing");
        BService bService = (BService) AopContext.currentProxy();
        bService.test();
    }

    public void test(){
        System.out.println("BService test");
    }
}

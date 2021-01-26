package com.mafashen.spring.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * @author mafashen
 * @since 2021-01-04.
 */
@Lazy
@Service
public class AService {

//    @Lazy
    @Autowired
    private BService bService;

    public void doSomeThing(){
        System.out.println("AService doSomeThing");
        bService.doSomeThing();
        this.test();
    }

    public void test(){
        System.out.println("AService test");
    }
}

package com.mafashen.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author mafashen
 * created on 2018/12/3.
 */
@Aspect
public class AdvisorBean {

	@Pointcut("execution(* *.test())")
	public void pointCut(){

	}

//	@Before("pointCut()")
	public void before(){
		System.out.println("before pointCut");
	}

//	@After("pointCut()")
	public void after(){
		System.out.println("after pointCut");
	}

	@Around("pointCut()")
	public void around(ProceedingJoinPoint point){
		System.out.println("before around");
		try {
			Object proceed = point.proceed();
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
		System.out.println("after around");
	}
}

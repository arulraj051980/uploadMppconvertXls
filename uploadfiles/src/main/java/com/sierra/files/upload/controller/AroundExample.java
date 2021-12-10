package com.sierra.files.upload.controller;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class AroundExample {
	
	@Around("execution(* com.sierra.files.upload.service..*(..)))")
	public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
		Object retVal = pjp.proceed();
		return retVal;
	}

}

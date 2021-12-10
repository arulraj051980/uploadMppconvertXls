package com.sierra.files.upload.loggerAOP;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class loggingAspect {
	
	 private static final Logger LOGGER = LogManager.getLogger(loggingAspect.class);
	 
	 @Around("execution(* com.sierra.files.upload.service..*(..)))")
	 public Object profileAllMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		 MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
		 
		 String className = methodSignature.getDeclaringType().getSimpleName();
		 String methodName = methodSignature.getName();
		 
		 final StopWatch stopWatch = new StopWatch();
		 
		 stopWatch.start();
		 Object result = proceedingJoinPoint.proceed();
		 stopWatch.stop();
		 
		 LOGGER.info("Execution time of "+className+ "." + methodName + "::" +stopWatch.getTotalTimeMillis()+" ms");
		 return result;		 
	 }
}

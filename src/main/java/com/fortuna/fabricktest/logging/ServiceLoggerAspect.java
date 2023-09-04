package com.fortuna.fabricktest.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceLoggerAspect {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceLoggerAspect.class);
	
	@Around(value = "execution(* com.fortuna.fabricktest.service..*.*(..))")
	public Object logService(ProceedingJoinPoint joinPoint) throws Throwable  {
		Object[] args = joinPoint.getArgs();
		
		LOGGER.debug("ServiceLogger method call || Method[{}.{}] || args[{}]", 
				joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName(),
				args);
		
		long start = System.currentTimeMillis();
		
		try {
			Object res = joinPoint.proceed();
			
			long end = System.currentTimeMillis();
		
			LOGGER.debug("ServiceLogger method return || Method[{}.{}] || args[{}] || return class[{}] || time ms[{}]", 
					joinPoint.getSignature().getDeclaringTypeName(),
					joinPoint.getSignature().getName(),
					args,
					res.getClass().getName(),
					end - start);
			
			return res;
		} catch(Throwable t) {
			long end = System.currentTimeMillis();
			
			LOGGER.debug("ServiceLogger method exception || Method[{}.{}] || args[{}] || exception[{}] || time ms[{}]", 
					joinPoint.getSignature().getDeclaringTypeName(),
					joinPoint.getSignature().getName(),
					args,
					t.getClass().getName(),
					end - start);
			
			throw t;
		}
	}
}

package com.fortuna.fabricktest.controller;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Aspect
@Component
public class RestLoggerAspect {
/*
    private static final Logger LOGGER = LoggerFactory.getLogger(RestLoggerAspect.class);
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Pointcut("@target(classRequestMapping) && @annotation(requestMapping) && within(com.fortuna.fabricktest.controller..*) )")
    public void pointcut(RequestMapping classRequestMapping, RequestMapping requestMapping)  {}
    
    @Before("pointcut(classRequestMapping, requestMapping)")
    public void logMethodRequest(JoinPoint joinPoint, RequestMapping classRequestMapping, RequestMapping requestMapping) {
        
        Map<String, Object> parameters = getParameters(joinPoint);
      
        String logMessage = "Request: endpoint: {} || method: {} || arguments: {} ";
      
        try {
            LOGGER.info(logMessage,
                    	requestMapping.path(),
                    	requestMapping.method(), 
                    	objectMapper.writeValueAsString(parameters));
        	
        } catch (JsonProcessingException e) {
        	LOGGER.error("RestLogger.logMethod(): Error while converting arguments", e);
        }
    }
    
    @AfterReturning(pointcut = "pointcut(classRequestMapping, requestMapping)", returning = "entity")
    public void logMethodResponse(JoinPoint joinPoint, RequestMapping classRequestMapping, RequestMapping requestMapping, ResponseEntity<?> entity ) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        RequestMapping mapping = signature.getMethod().getAnnotation(RequestMapping.class);

        String logMessage = "Response: endpoint: {} || method: {} || entity: {} ";
        try {
        	LOGGER.info(logMessage,
                    mapping.path(), 
                    mapping.method(), 
                    objectMapper.writeValueAsString(entity));
        } catch (JsonProcessingException e) {
        	LOGGER.error("RestLogger.logMethod(): Error while converting response", e);
        }
    }
    
    @Around("pointcut(classRequestMapping, requestMapping)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint, RequestMapping classRequestMapping, RequestMapping requestMapping) throws Throwable {
        final StopWatch stopWatch = new StopWatch();
       
        stopWatch.start();

        Object proceed = joinPoint.proceed();

        stopWatch.stop();

        LOGGER.info("\"{}\" executed in {} ms", requestMapping.path(), stopWatch.getTotalTimeMillis());

        return proceed;
    }
    
    private Map<String, Object> getParameters(JoinPoint joinPoint) {
        CodeSignature signature = (CodeSignature) joinPoint.getSignature();

        HashMap<String, Object> map = new HashMap<>();

        String[] parameterNames = signature.getParameterNames();

        for (int i = 0; i < parameterNames.length; i++) {
            map.put(parameterNames[i], joinPoint.getArgs()[i]);
        }

        return map;
    }
*/
}

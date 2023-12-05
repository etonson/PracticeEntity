package com.example.demo.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class AopLog {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	ThreadLocal<Long> startTime = new ThreadLocal<Long>();

	@Pointcut("execution(public * com.example..*.*(..))")
	public void aopWebLog() {
	}

	@Before("aopWebLog()")
	public void doBefore(JoinPoint joinPoint) {
		try {
			startTime.set(System.currentTimeMillis());
			ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes();
			HttpServletRequest request = attributes.getRequest();
			logger.info("URL:" + request.getRequestURL().toString());
			logger.info("HTTP方法: " + request.getMethod());
			logger.info("IP地址:" + request.getRemoteAddr());
			logger.info("類的名稱 : " + joinPoint.getSignature().getDeclaringTypeName() + "."
					+ joinPoint.getSignature().getName());
			logger.info("参数 : " + request.getQueryString());
		} catch (Exception e) {
		}
	}

	@AfterReturning(pointcut = "aopWebLog()", returning = "retObject")
	public void doAfterReturning(Object retObject) throws Throwable {
		logger.info("response : " + retObject);
		logger.info("時長: " + (System.currentTimeMillis() - startTime.get()));
	}

	@AfterThrowing(pointcut = "aopWebLog()", throwing = "ex")
	public void addAfterThrowingLogger(JoinPoint joinPoint, Exception ex) {
		logger.error("例外", ex);
	}
}


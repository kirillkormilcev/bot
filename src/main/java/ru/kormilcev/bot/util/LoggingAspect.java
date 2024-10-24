package ru.kormilcev.bot.util;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

  @Pointcut("""
within(ru.kormilcev.bot..*)
""")
  public void anyMethodsExceptJwtFilter() {}

  @Before("""
      anyMethodsExceptJwtFilter()
      && !@annotation(org.springframework.scheduling.annotation.Scheduled)
      """)
  public void logBefore(JoinPoint joinPoint){
    String className = joinPoint.getSignature().getDeclaringTypeName();
    String methodName = joinPoint.getSignature().getName();
    log.info("{}.{}", className, methodName);
  }

  @AfterReturning(value = "anyMethodsExceptJwtFilter()", returning = "returning")
  public void logAfter(JoinPoint joinPoint, Object returning) {
    String className = joinPoint.getSignature().getDeclaringTypeName();
    String methodName = joinPoint.getSignature().getName();

    if (joinPoint.getArgs().length == 0) {
      if (returning != null) {
        log.debug("{}.{}() returning: {}", className, methodName, returning);
      } else {
        log.debug("{}.{}()", className, methodName);
      }
    } else {
      Object[] args = joinPoint.getArgs();

      if (returning != null) {
        log.debug("{}.{}(args: {}) returning: {}", className, methodName, args, returning);
      } else {
        log.debug("{}.{}(args: {})", className, methodName, args);
      }
    }
  }

  @AfterThrowing(value = "anyMethodsExceptJwtFilter()", throwing = "e")
  public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
    String eName = e.getClass().getName();
    String className = joinPoint.getSignature().getDeclaringTypeName();
    String methodName = joinPoint.getSignature().getName();
    String eMessage = e.getMessage();

    log.error("{} occurred in method: {}.{} with message: {}",
        eName, className, methodName, eMessage);
  }
}

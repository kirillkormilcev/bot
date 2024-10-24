package ru.kormilcev.bot.util;

import java.util.Arrays;
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
  public void anyMethods() {}

  @Before("""
      anyMethods()
      && !@annotation(org.springframework.scheduling.annotation.Scheduled)
      """)
  public void logBefore(JoinPoint joinPoint){
    String className = joinPoint.getSignature().getDeclaringTypeName();
    String methodName = joinPoint.getSignature().getName();
    log.info("{}.{}", className, methodName);
  }

  @AfterReturning(value = "anyMethods()", returning = "returning")
  public void logAfter(JoinPoint joinPoint, Object returning) {
    String className = joinPoint.getSignature().getDeclaringTypeName();
    String methodName = joinPoint.getSignature().getName();

    StringBuilder logMessage = new StringBuilder(className).append(".").append(methodName);

    if (joinPoint.getArgs().length > 0) {
      logMessage.append("(args: ").append(Arrays.toString(joinPoint.getArgs())).append(")");
    }
    if (returning != null) {
      logMessage.append("(returning: ").append(returning).append(".");
    }

    log.debug(logMessage.toString());
  }

  @AfterThrowing(value = "anyMethods()", throwing = "e")
  public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
    String eName = e.getClass().getName();
    String className = joinPoint.getSignature().getDeclaringTypeName();
    String methodName = joinPoint.getSignature().getName();
    String eMessage = e.getMessage();

    log.error("{} occurred in method: {}.{} with message: {}",
        eName, className, methodName, eMessage);
  }
}

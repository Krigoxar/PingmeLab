package com.pingme.ping.components;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/** The type Logging aspect. */
@Aspect
@Component
public class LoggingAspect {
  private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

  @Pointcut("execution(* com.pingme.ping.controllers.*.create*(..))")
  public void add() {}

  @Pointcut("execution(* com.pingme.ping.controllers.*.delete*(..))")
  public void delete() {}

  @Pointcut("execution(* com.pingme.ping.controllers.*.update*(..))")
  public void update() {}

  @AfterReturning(pointcut = "add()", returning = "result")
  public void logAdd(Object result) {
    logger.info("Add: {}", result);
  }

  @AfterReturning(pointcut = "delete()", returning = "result")
  public void logDelete(Object result) {
    logger.info("delete: {}", result);
  }

  @AfterReturning(pointcut = "update()", returning = "result")
  public void logUpdate(Object result) {
    logger.info("Updated: {}", result);
  }
}

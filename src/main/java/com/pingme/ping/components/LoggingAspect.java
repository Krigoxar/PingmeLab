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

  @Pointcut("execution(* com.pingme.ping.controllers.CategoryController.*(..))")
  public void countCategory() {}

  @Pointcut("execution(* com.pingme.ping.controllers.ObservationsController.*(..))")
  public void countObservations() {}

  @Pointcut("execution(* com.pingme.ping.controllers.StatsController.*(..))")
  public void countStats() {}

  @Pointcut("execution(* com.pingme.ping.controllers.UrlController.*(..))")
  public void countUrl() {}

  /** The Function. */
  @AfterReturning(pointcut = "countCategory()")
  public void exCountCategory() {
    var inst = MultitonCounter.getInstance("Category");
    inst.count();
    var res = String.format("Category count: %d.", inst.getCount());
    logger.info(res);
  }

  /** The Function. */
  @AfterReturning(pointcut = "countObservations()")
  public void excountObservations() {
    var inst = MultitonCounter.getInstance("Observations");
    inst.count();
    var res = String.format("Observations count: %d.", inst.getCount());
    logger.info(res);
  }

  /** The Function. */
  @AfterReturning(pointcut = "countStats()")
  public void excountStats() {
    var inst = MultitonCounter.getInstance("Stats");
    inst.count();
    var res = String.format("Stats count: %d.", inst.getCount());
    logger.info(res);
  }

  /** The Function. */
  @AfterReturning(pointcut = "countUrl()")
  public void excountUrl() {
    var inst = MultitonCounter.getInstance("Url");
    inst.count();
    var res = String.format("Url count: %d.", inst.getCount());
    logger.info(res);
  }
}

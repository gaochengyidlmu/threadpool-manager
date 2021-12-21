package com.gcy.baiji.server.aspect;


import com.gcy.baiji.tools.cache.LruCache;
import com.gcy.baiji.tools.cache.LruEvictableCache;
import java.util.ArrayList;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CacheAspect {

  private final LruCache<String, Object> cache = new LruEvictableCache<>(1 << 10,
      1 << 6);

  StandardReflectionParameterNameDiscoverer parameterNameDiscoverer = new StandardReflectionParameterNameDiscoverer();

  /**
   * Following is the definition for a pointcut to select
   * all the methods available. So advice will be called
   * for all the methods.
   */
  @Pointcut("@annotation(com.gcy.baiji.server.aspect.Cached)")
  private void selectedCached() {
  }

  /**
   * This is the method which I would like to execute
   * around a selected method execution.
   */
  @Around("selectedCached()")
  public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    System.out.println("Around advice");
    Object[] params = proceedingJoinPoint.getArgs();

    MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
    Cached annotation = AnnotationUtils.findAnnotation(signature.getMethod(), Cached.class);
    long ttl = annotation.ttl();
    long deadline = System.currentTimeMillis() + ttl;

    // 创建一个虚拟的容器EvaluationContext
    StandardEvaluationContext ctx = new StandardEvaluationContext();
    ctx.setRootObject(new ArrayList<>());
//    ctx.setVariable("params", params);

    signature.getParameterNames();
    String[] paramNames = parameterNameDiscoverer.getParameterNames(signature.getMethod());
    for (int i = 0; i < params.length; i++) {
      ctx.setVariable(paramNames[i], params[i]);
    }

    for (int i = 0; i < params.length; i++) {
      ctx.setVariable(String.valueOf(i), params[i]);
    }

    // 创建ExpressionParser解析表达式
    ExpressionParser parser = new SpelExpressionParser();
    // 表达式放置
    Expression namespaceExp = parser.parseExpression(annotation.namespace());
    Expression keyExp = parser.parseExpression(annotation.key());

    String namespace = (String) namespaceExp.getValue(ctx);
    String key = (String) keyExp.getValue(ctx);

    String cacheKey = namespace + "-" + key;
    Object entity = cache.get(cacheKey);
    if (entity != null) {
      return entity;
    }

    Object result = proceedingJoinPoint.proceed(params);
    cache.put(cacheKey, result, deadline);
    return result;
  }
}

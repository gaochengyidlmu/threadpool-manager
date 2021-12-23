package com.gcy.baiji.server.aspect;


import com.gcy.baiji.tools.cache.LruCache;
import com.gcy.baiji.tools.cache.LruEvictableCache;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class CacheAspect {

  private final LruCache<String, Object> cache = new LruEvictableCache<>(1 << 10,
      1 << 6);

  @Autowired
  private ParameterNameDiscoverer parameterNameDiscoverer;

  /**
   * 定义 Cached 注解修饰的切点
   */
  @Pointcut("@annotation(com.gcy.baiji.server.aspect.Cached)")
  private void selectedCached() {
  }

  /**
   * Following is the definition for a pointcut to select
   * all the methods available. So advice will be called
   * for all the methods.
   */
  @Pointcut("@annotation(com.gcy.baiji.server.aspect.EvictCache)")
  private void selectedEvictCache() {
  }

  /**
   * This is the method which I would like to execute
   * around a selected method execution.
   */
  @Around("selectedCached()")
  public Object aroundCached(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
    Cached annotation = AnnotationUtils.findAnnotation(signature.getMethod(), Cached.class);
    String cacheKey = buildCacheKey(signature.getParameterNames(), proceedingJoinPoint.getArgs(),
        annotation.namespace(), annotation.key());

    Object entity = cache.get(cacheKey);
    if (entity != null) {
      log.info("Cache hit {}", entity);
      return entity;
    }

    Object result = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());

    long ttl = annotation.ttl();
    long deadline = System.currentTimeMillis() + ttl;
    cache.put(cacheKey, result, deadline);

    log.info("Cache missed, put cache {}", result);
    return result;
  }

  /**
   * 数据更新时，将对应的缓存进行无效
   */
  @Around("selectedEvictCache()")
  public Object aroundEvictCache(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    Object result = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());

    MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
    EvictCache annotation = AnnotationUtils.findAnnotation(signature.getMethod(), EvictCache.class);
    String cacheKey = buildCacheKey(signature.getParameterNames(), proceedingJoinPoint.getArgs(),
        annotation.namespace(), annotation.key());
    cache.remove(cacheKey);
    log.info("remove cache {}", cacheKey);
    return result;
  }

  private String buildCacheKey(String[] parameterNames, Object[] params, String namespaceAnno,
      String keyAnno) {
    // 创建一个虚拟的容器EvaluationContext
    StandardEvaluationContext ctx = new StandardEvaluationContext();
    // 将参数名字写入，此处需要 javac 中添加 -parameters 参数，该参数会让 class 文件中保存参数实际名称
    for (int i = 0; i < params.length; i++) {
      ctx.setVariable(parameterNames[i], params[i]);
    }

    // 创建ExpressionParser解析表达式
    ExpressionParser parser = new SpelExpressionParser();
    // 表达式放置
    Expression namespaceExp = parser.parseExpression(namespaceAnno);
    Expression keyExp = parser.parseExpression(keyAnno);

    String namespace = (String) namespaceExp.getValue(ctx);
    String key = (String) keyExp.getValue(ctx);

    return namespace + "-" + key;
  }
}

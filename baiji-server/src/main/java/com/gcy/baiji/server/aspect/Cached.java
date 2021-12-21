package com.gcy.baiji.server.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cached {

  String namespace();

  String key();

  // 存活时长，毫秒
  long ttl() default 10 * 1000;
}

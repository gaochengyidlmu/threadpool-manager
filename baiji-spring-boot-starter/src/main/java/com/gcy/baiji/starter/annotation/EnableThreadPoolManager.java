package com.gcy.baiji.starter.annotation;

import com.gcy.baiji.starter.autoConfiguration.ThreadPoolManagerConfiguration;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ThreadPoolManagerConfiguration.class)
public @interface EnableThreadPoolManager {

}

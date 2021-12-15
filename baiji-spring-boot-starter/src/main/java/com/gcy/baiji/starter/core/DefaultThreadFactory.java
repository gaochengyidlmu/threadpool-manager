package com.gcy.baiji.starter.core;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import org.jetbrains.annotations.NotNull;

public class DefaultThreadFactory implements ThreadFactory {

  private final String prefix;
  private final ThreadGroup threadGroup;
  private final AtomicInteger threadNum = new AtomicInteger(1);

  public DefaultThreadFactory(String prefix) {
    this.prefix = prefix;
    this.threadGroup = new ThreadGroup(prefix);
  }

  @Override
  public Thread newThread(@NotNull Runnable r) {
    return new Thread(threadGroup, r, prefix + "-" + threadNum.getAndIncrement());
  }
}

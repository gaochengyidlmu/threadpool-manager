package com.gcy.baiji.client.monitor;

import com.gcy.baiji.common.enums.ThreadPoolStateEnum;
import com.gcy.baiji.common.tool.Reflections;
import java.lang.reflect.Field;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 默认对 JDK 线程池的监控实现
 */
public class JDKThreadPoolMonitor extends AbstractThreadPoolMonitor {

  private final ThreadPoolExecutor executor;

  public JDKThreadPoolMonitor(String name, ThreadPoolExecutor executor) {
    super(name);
    this.executor = executor;
  }

  public int getActiveThreadNum() {
    return executor.getActiveCount();
  }

  public int getIdleThreadNum() {
    return executor.getPoolSize() - executor.getActiveCount();
  }

  public int getCurrentTaskCount() {
    return executor.getQueue().size();
  }

  public long getTotalTaskCount() {
    return executor.getTaskCount();
  }

  public int getLargestPoolSize() {
    return executor.getLargestPoolSize();
  }

  public ThreadPoolStateEnum getRunState() {
    int state = 0;
    try {
      Field ctlField = Reflections.getField(executor.getClass(), "ctl");
      ctlField.setAccessible(true);
      state = runStateOf(((AtomicInteger) ctlField.get(executor)).get());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return ThreadPoolStateEnum.of(state);
  }

  private static int runStateOf(int c) {
    return c & ~((1 << Integer.SIZE - 3) - 1);
  }


  /*
   * 以下是对 ThreadPoolConfig 的实现
   */

  public int getCorePoolSize() {
    return executor.getCorePoolSize();
  }

  public int getMaximumPoolSize() {
    return executor.getMaximumPoolSize();
  }

  public Class<? extends BlockingQueue<Runnable>> getBlockingQueueClass() {
    return (Class<? extends BlockingQueue<Runnable>>) executor.getQueue().getClass();
  }

  public Class<? extends RejectedExecutionHandler> getRejectedExecutionHandlerClass() {
    return executor.getRejectedExecutionHandler().getClass();
  }
}

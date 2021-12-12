package com.gcy.baiji.client.monitor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;

/**
 * 线程池配置信息
 * 1. 获取配置的核心线程数
 * 2. 获取配置的最大线程数
 * 3. 获取配置的阻塞队列类型
 * 4. 获取配置的拒绝策略
 */
public interface ThreadPoolConfig {

  int getCorePoolSize();

  int getMaximumPoolSize();

  Class<? extends BlockingQueue<Runnable>> getBlockingQueueClass();

  Class<? extends RejectedExecutionHandler> getRejectedExecutionHandlerClass();
}

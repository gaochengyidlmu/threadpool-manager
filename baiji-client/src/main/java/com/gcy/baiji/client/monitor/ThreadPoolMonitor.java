package com.gcy.baiji.client.monitor;

import com.gcy.baiji.common.core.ThreadPoolSnapshot;
import com.gcy.baiji.common.enums.ThreadPoolStateEnum;

/**
 * 线程池监控器
 * 动态数据监控维度：
 * 1. 获取线程池当前活动的线程数量
 * 2. 获取线程池当前 idle 的线程数量
 * 3. 获取线程池当前阻塞队列的任务数量
 * 4. 获取线程池总共提交的任务数量
 * 5. 获取线程池最大的线程数
 * 6. 获取线程池当前状态
 */
public interface ThreadPoolMonitor extends ThreadPoolConfig {

  String getName();

  int getActiveThreadNum();

  int getIdleThreadNum();

  int getCurrentTaskCount();

  long getTotalTaskCount();

  int getLargestPoolSize();

  ThreadPoolStateEnum getRunState();

  ThreadPoolSnapshot snapshot();
}

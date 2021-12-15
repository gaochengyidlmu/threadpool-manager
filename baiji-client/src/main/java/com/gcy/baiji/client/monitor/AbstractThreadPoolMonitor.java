package com.gcy.baiji.client.monitor;

import com.gcy.baiji.common.vo.ThreadPoolSnapshot;
import java.util.Date;

public abstract class AbstractThreadPoolMonitor implements ThreadPoolMonitor {

  // 线程池名称
  private final String name;

  public String getName() {
    return name;
  }

  public AbstractThreadPoolMonitor(String name) {
    this.name = name;
  }

  public ThreadPoolSnapshot snapshot() {
    return ThreadPoolSnapshot.Builder().activeThreadNum(getActiveThreadNum())
        .corePoolSize(getCorePoolSize()).currentTaskCount(getCurrentTaskCount())
        .idleThreadNum(getIdleThreadNum()).largestPoolSize(getLargestPoolSize())
        .maximumPoolSize(getMaximumPoolSize()).runState(getRunState()).snapshotTime(new Date())
        .threadPoolName(getName()).totalTaskCount(getTotalTaskCount()).build();
  }
}

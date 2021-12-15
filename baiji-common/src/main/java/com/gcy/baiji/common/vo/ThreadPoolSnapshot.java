package com.gcy.baiji.common.vo;

import com.gcy.baiji.common.enums.ThreadPoolStateEnum;
import java.util.Date;

public class ThreadPoolSnapshot {

  private String threadPoolName;
  private Date snapshotTime;
  private int activeThreadNum;
  private int idleThreadNum;
  private int currentTaskCount;
  private long totalTaskCount;
  private int largestPoolSize;
  private ThreadPoolStateEnum runState;
  private int corePoolSize;
  private int maximumPoolSize;

  public ThreadPoolSnapshot() {
  }

  public ThreadPoolSnapshot(Builder builder) {
    this.threadPoolName = builder.threadPoolName;
    this.snapshotTime = builder.snapshotTime;
    this.activeThreadNum = builder.activeThreadNum;
    this.idleThreadNum = builder.idleThreadNum;
    this.currentTaskCount = builder.currentTaskCount;
    this.totalTaskCount = builder.totalTaskCount;
    this.largestPoolSize = builder.largestPoolSize;
    this.runState = builder.runState;
    this.corePoolSize = builder.corePoolSize;
    this.maximumPoolSize = builder.maximumPoolSize;
  }

  public static Builder Builder() {
    return new Builder();
  }

  public static final class Builder {

    String threadPoolName;
    Date snapshotTime;
    int activeThreadNum;
    int idleThreadNum;
    int currentTaskCount;
    long totalTaskCount;
    int largestPoolSize;
    ThreadPoolStateEnum runState;
    int corePoolSize;
    int maximumPoolSize;

    public Builder threadPoolName(String threadPoolName) {
      this.threadPoolName = threadPoolName;
      return this;
    }

    public Builder snapshotTime(Date snapshotTime) {
      this.snapshotTime = snapshotTime;
      return this;
    }

    public Builder activeThreadNum(int activeThreadNum) {
      this.activeThreadNum = activeThreadNum;
      return this;
    }

    public Builder idleThreadNum(int idleThreadNum) {
      this.idleThreadNum = idleThreadNum;
      return this;
    }

    public Builder currentTaskCount(int currentTaskCount) {
      this.currentTaskCount = currentTaskCount;
      return this;
    }

    public Builder totalTaskCount(long totalTaskCount) {
      this.totalTaskCount = totalTaskCount;
      return this;
    }

    public Builder largestPoolSize(int largestPoolSize) {
      this.largestPoolSize = largestPoolSize;
      return this;
    }

    public Builder runState(ThreadPoolStateEnum runState) {
      this.runState = runState;
      return this;
    }

    public Builder corePoolSize(int corePoolSize) {
      this.corePoolSize = corePoolSize;
      return this;
    }

    public Builder maximumPoolSize(int maximumPoolSize) {
      this.maximumPoolSize = maximumPoolSize;
      return this;
    }

    public ThreadPoolSnapshot build() {
      return new ThreadPoolSnapshot(this);
    }
  }

  public String getThreadPoolName() {
    return threadPoolName;
  }

  public void setThreadPoolName(String threadPoolName) {
    this.threadPoolName = threadPoolName;
  }

  public Date getSnapshotTime() {
    return snapshotTime;
  }

  public void setSnapshotTime(Date snapshotTime) {
    this.snapshotTime = snapshotTime;
  }

  public int getActiveThreadNum() {
    return activeThreadNum;
  }

  public void setActiveThreadNum(int activeThreadNum) {
    this.activeThreadNum = activeThreadNum;
  }

  public int getIdleThreadNum() {
    return idleThreadNum;
  }

  public void setIdleThreadNum(int idleThreadNum) {
    this.idleThreadNum = idleThreadNum;
  }

  public int getCurrentTaskCount() {
    return currentTaskCount;
  }

  public void setCurrentTaskCount(int currentTaskCount) {
    this.currentTaskCount = currentTaskCount;
  }

  public long getTotalTaskCount() {
    return totalTaskCount;
  }

  public void setTotalTaskCount(long totalTaskCount) {
    this.totalTaskCount = totalTaskCount;
  }

  public int getLargestPoolSize() {
    return largestPoolSize;
  }

  public void setLargestPoolSize(int largestPoolSize) {
    this.largestPoolSize = largestPoolSize;
  }

  public ThreadPoolStateEnum getRunState() {
    return runState;
  }

  public void setRunState(ThreadPoolStateEnum runState) {
    this.runState = runState;
  }

  public int getCorePoolSize() {
    return corePoolSize;
  }

  public void setCorePoolSize(int corePoolSize) {
    this.corePoolSize = corePoolSize;
  }

  public int getMaximumPoolSize() {
    return maximumPoolSize;
  }

  public void setMaximumPoolSize(int maximumPoolSize) {
    this.maximumPoolSize = maximumPoolSize;
  }
}

package com.gcy.baiji.common.enums;

public enum ThreadPoolStateEnum {
  RUNNING(-1 << Integer.SIZE - 3),
  SHUTDOWN(0 << Integer.SIZE - 3),
  STOP(1 << Integer.SIZE - 3),
  TIDYING(2 << Integer.SIZE - 3),
  TERMINATED(3 << Integer.SIZE - 3);

  private final int value;

  ThreadPoolStateEnum(int value) {
    this.value = value;
  }

  public static ThreadPoolStateEnum of(int value) {
    for (ThreadPoolStateEnum threadPoolStateEnum : ThreadPoolStateEnum.values()) {
      if (threadPoolStateEnum.value == value) {
        return threadPoolStateEnum;
      }
    }
    return null;
  }
}

package com.gcy.baiji.example.client;

import com.gcy.baiji.client.holder.Holder;
import com.gcy.baiji.client.holder.ThreadPoolMonitorHolder;
import com.gcy.baiji.client.monitor.JDKThreadPoolMonitor;
import com.gcy.baiji.client.monitor.ThreadPoolMonitor;
import com.gcy.baiji.common.vo.ThreadPoolSnapshot;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MonitorApplication {

  public static void main(String[] args) {
    MonitorApplication monitorApplication = new MonitorApplication();
    ThreadPoolExecutor executor = monitorApplication.createExecutor();
    Holder<String, ThreadPoolMonitor> holder = new ThreadPoolMonitorHolder();
    ThreadPoolMonitor monitor = new JDKThreadPoolMonitor("example-executor", executor);
    holder.put(monitor.getName(), monitor);
    for (int i = 0; i < 20; i++) {
      executor.execute(() -> {
        try {
          Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      });
    }

    ThreadPoolSnapshot snapshot = holder.get(monitor.getName()).snapshot();

    System.out.println(snapshot);

    executor.shutdownNow();

    snapshot = holder.get(monitor.getName()).snapshot();

    System.out.println(snapshot);

    try {
      Thread.sleep(3 * 1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    snapshot = holder.get(monitor.getName()).snapshot();

    System.out.println(snapshot);
  }

  ThreadPoolExecutor createExecutor() {
    return new ThreadPoolExecutor(5, 10, 60, TimeUnit.SECONDS,
        new LinkedBlockingDeque<>(20), createThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
  }

  ThreadFactory createThreadFactory() {
    return new ThreadFactory() {
      private int count = 0;

      @Override
      public Thread newThread(Runnable r) {
        return new Thread(r, "Thread-" + ++count);
      }
    };
  }
}

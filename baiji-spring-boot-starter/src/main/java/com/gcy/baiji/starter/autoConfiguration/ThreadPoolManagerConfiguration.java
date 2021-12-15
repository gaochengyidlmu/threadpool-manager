package com.gcy.baiji.starter.autoConfiguration;

import com.gcy.baiji.client.holder.ThreadPoolMonitorHolder;
import com.gcy.baiji.client.monitor.JDKThreadPoolMonitor;
import com.gcy.baiji.client.report.HttpSnapshotReporter;
import com.gcy.baiji.common.client.ThreadPoolSnapshotClient;
import com.gcy.baiji.common.client.impl.ThreadPoolSnapshotClientImpl;
import com.gcy.baiji.starter.core.DefaultThreadFactory;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties(BaijiProperties.class)
@ConditionalOnProperty(name = "spring.baiji.thread-pool.enabled", havingValue = "true")
public class ThreadPoolManagerConfiguration {

  @Autowired
  private BaijiProperties baijiProperties;

  @Bean
  @ConditionalOnMissingBean(ThreadPoolSnapshotClient.class)
  ThreadPoolSnapshotClient buildThreadPoolSnapshotClient() {
    return new ThreadPoolSnapshotClientImpl(baijiProperties.getServerHost());
  }

  @Bean
  @ConditionalOnMissingBean(HttpSnapshotReporter.class)
  HttpSnapshotReporter buildHttpSnapshotReporter(ThreadPoolSnapshotClient client) {
    return new HttpSnapshotReporter(client);
  }

  @Bean
  @ConditionalOnMissingBean(ThreadPoolMonitorHolder.class)
  ThreadPoolMonitorHolder buildThreadPoolMonitorHolder() {
    return new ThreadPoolMonitorHolder();
  }

  @Bean("baijiScheduledExecutorService")
  @ConditionalOnMissingBean(name = "baijiScheduledExecutorService")
  ScheduledExecutorService buildScheduledExecutorService(HttpSnapshotReporter reporter,
      ThreadPoolMonitorHolder threadPoolMonitorHolder) {

    // 创建大小为5的线程池
    ScheduledExecutorService scheduledThreadPool = Executors
        .newScheduledThreadPool(5, new DefaultThreadFactory("baiji-thread-pool-manager"));

    JDKThreadPoolMonitor baijiScheduleThreadPool = new JDKThreadPoolMonitor(
        "baijiScheduleThreadPool", (ThreadPoolExecutor) scheduledThreadPool);
    threadPoolMonitorHolder
        .put(baijiScheduleThreadPool.getName(), baijiScheduleThreadPool);

    // 周期性执行，每5秒执行一次
    scheduledThreadPool.scheduleAtFixedRate(() -> {
          threadPoolMonitorHolder.keySet()
              .forEach(key -> reporter.report(threadPoolMonitorHolder.get(key).snapshot()));
        }, 0, 5,
        TimeUnit.SECONDS);
    return scheduledThreadPool;
  }
}

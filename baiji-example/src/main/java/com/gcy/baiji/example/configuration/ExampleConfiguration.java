package com.gcy.baiji.example.configuration;

import com.gcy.baiji.client.holder.ThreadPoolMonitorHolder;
import com.gcy.baiji.client.monitor.JDKThreadPoolMonitor;
import com.gcy.baiji.client.monitor.ThreadPoolMonitor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExampleConfiguration {

  @Bean("commonThreadPoolExecutor")
  ThreadPoolExecutor buildThreadPoolExecutor() {
    return new ThreadPoolExecutor(10, 50, 120, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
  }

  @Bean
  ThreadPoolMonitorHolder buildThreadPoolMonitorHolder(
      @Qualifier("commonThreadPoolExecutor") ThreadPoolExecutor threadPoolExecutor) {
    ThreadPoolMonitorHolder threadPoolMonitorHolder = new ThreadPoolMonitorHolder();
    ThreadPoolMonitor monitor = new JDKThreadPoolMonitor("commonThreadPoolExecutor",
        threadPoolExecutor);

    threadPoolMonitorHolder.put(monitor.getName(), monitor);
    return threadPoolMonitorHolder;
  }
}

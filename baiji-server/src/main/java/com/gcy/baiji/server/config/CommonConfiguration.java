package com.gcy.baiji.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

@Configuration
public class CommonConfiguration {

  @Bean
  public ParameterNameDiscoverer parameterNameDiscoverer() {
    return new DefaultParameterNameDiscoverer();
  }
}

package com.gcy.baiji.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan(basePackages = {"com.gcy.baiji.server.mapper"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(ServerApplication.class);
  }
}

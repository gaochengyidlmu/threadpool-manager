package com.gcy.baiji.example;

import com.gcy.baiji.starter.annotation.EnableThreadPoolManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableThreadPoolManager
public class ExampleApplication {

  public static void main(String[] args) {
    SpringApplication.run(ExampleApplication.class);
  }
}

package com.gcy.baiji.example.controller;

import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

  private int count;

  @Autowired
  @Qualifier("commonThreadPoolExecutor")
  ThreadPoolExecutor threadPoolExecutor;

  @GetMapping
  public String getHello() {
    threadPoolExecutor.execute(() -> System.out.println("count: " + count++));
    return "ok";
  }
}

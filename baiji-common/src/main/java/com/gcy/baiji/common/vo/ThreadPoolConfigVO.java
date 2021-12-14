package com.gcy.baiji.common.vo;

import java.util.Date;

public class ThreadPoolConfigVO {

  // 主键ID
  private Long id;
  // 应用名称
  private String app_name;
  // 线程池名称
  private String tp_name;
  // 核心线程数
  private Integer core_size;
  // 最大线程数
  private Integer max_size;
  // 队列类型
  private String queue_type;
  // 队列大小
  private Integer queue_capacity;
  // 拒绝策略
  private String rejected_type;
  // 线程存活时间
  private Integer keep_alive_time;
  // 创建时间
  private Date created_at;
  // 修改时间
  private Date updated_at;
}

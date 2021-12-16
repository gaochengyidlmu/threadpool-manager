package com.gcy.baiji.server.entity;

import com.gcy.baiji.common.enums.ThreadPoolStateEnum;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Alias("ThreadPoolConfigEntity")
public class ThreadPoolConfigEntity {

  // 主键ID
  private Integer id;
  // 应用名称
  private String applicationName;
  // 线程池名称
  private String threadPoolName;
  // 线程状态
  private ThreadPoolStateEnum runState;
  // 核心线程数
  private Integer corePoolSize;
  // 最大线程数
  private Integer maximumPoolSize;
  // 队列类型
  private String queueType;
  // 队列大小
  private Integer queueCapacity;
  // 拒绝策略
  private String rejectedType;
  // 线程存活时间
  private Integer keepAliveTime;
  // 创建时间
  private Date createdAt;
  // 修改时间
  private Date updatedAt;
}

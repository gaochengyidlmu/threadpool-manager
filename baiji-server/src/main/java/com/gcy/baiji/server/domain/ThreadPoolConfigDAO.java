package com.gcy.baiji.server.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

@TableName("baiji_tp_config")
@Data
public class ThreadPoolConfigDAO {

  // 主键ID
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
  // 应用名称
  private String appName;
  // 线程池名称
  private String tpName;
  // 核心线程数
  private Integer coreSize;
  // 最大线程数
  private Integer maxSize;
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

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
@Alias("ThreadPoolSnapshotEntity")
public class ThreadPoolSnapshotEntity {

  // 主键ID
  private Integer id;
  // 外键 tp id
  private Integer tpConfigId;
  // ip
  private String ip;
  // port
  private String port;
  // 线程状态
  private ThreadPoolStateEnum runState;
  // 活动线程数
  private Integer activeThreadNum;
  // 空闲线程数
  private Integer idleThreadNum;
  // 当前剩余任务数
  private Integer currentTaskCount;
  // 总任务数
  private Long totalTaskCount;
  // 快照时间
  private Date snapshotTime;
  // 创建时间
  private Date createdAt;
  // 修改时间
  private Date updatedAt;
}
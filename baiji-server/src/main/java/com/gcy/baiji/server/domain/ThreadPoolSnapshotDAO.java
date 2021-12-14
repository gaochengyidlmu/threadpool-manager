package com.gcy.baiji.server.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

@TableName("baiji_tp_snapshot")
@Data
public class ThreadPoolSnapshotDAO {

  // 主键ID
  private Long id;
  // 外键 tp id
  private Long tpId;
  // ip
  private String ip;
  // port
  private Integer port;
  // 活动线程数
  private Integer activeThreadNum;
  // 空闲线程数
  private Integer idleThreadNum;
  // 当前剩余任务数
  private Integer currentTaskCount;
  // 总任务数
  private Long totalTaskCount;
  // 创建时间
  private Date createdAt;
  // 修改时间
  private Date updatedAt;


}
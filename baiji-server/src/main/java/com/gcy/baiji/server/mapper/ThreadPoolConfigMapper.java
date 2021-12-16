package com.gcy.baiji.server.mapper;

import com.gcy.baiji.server.entity.ThreadPoolConfigEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ThreadPoolConfigMapper {

  @Select("SELECT * FROM baiji_tp_config WHERE application_name = #{applicationName} AND thread_pool_name = #{threadPoolName} LIMIT 0, 1")
  ThreadPoolConfigEntity selectOneByApplicationNameAndThreadPoolName(
      @Param("applicationName") String applicationName,
      @Param("threadPoolName") String threadPoolName);

  void insert(ThreadPoolConfigEntity entity);

  void update(ThreadPoolConfigEntity entity);
}

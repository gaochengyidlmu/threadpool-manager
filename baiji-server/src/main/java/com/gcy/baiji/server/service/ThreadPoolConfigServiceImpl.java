package com.gcy.baiji.server.service;

import com.gcy.baiji.common.tool.ObjectUtils;
import com.gcy.baiji.common.vo.ThreadPoolSnapshot;
import com.gcy.baiji.server.entity.ThreadPoolConfigEntity;
import com.gcy.baiji.server.mapper.ThreadPoolConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThreadPoolConfigServiceImpl implements ThreadPoolConfigService {

  @Autowired
  private ThreadPoolConfigMapper mapper;

  @Override
  // 保存或更新
  public ThreadPoolConfigEntity save(String applicationName,
      ThreadPoolSnapshot threadPoolSnapshot) {
    ThreadPoolConfigEntity threadPoolConfigEntity = mapper
        .selectOneByApplicationNameAndThreadPoolName(applicationName,
            threadPoolSnapshot.getThreadPoolName());
    if (threadPoolConfigEntity == null) {
      threadPoolConfigEntity = ThreadPoolConfigEntity.builder().applicationName(applicationName)
          .build();
      ObjectUtils.merge(threadPoolSnapshot, threadPoolConfigEntity);
      mapper.insert(threadPoolConfigEntity);
    } else {
      ObjectUtils.merge(threadPoolSnapshot, threadPoolConfigEntity);
      mapper.update(threadPoolConfigEntity);
    }

    return threadPoolConfigEntity;
  }
}

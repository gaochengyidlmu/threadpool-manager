package com.gcy.baiji.server.service;

import com.gcy.baiji.common.utils.ObjectUtils;
import com.gcy.baiji.common.vo.ThreadPoolSnapshot;
import com.gcy.baiji.server.entity.ThreadPoolConfigEntity;
import com.gcy.baiji.server.mapper.ThreadPoolConfigMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ThreadPoolConfigServiceImpl implements ThreadPoolConfigService {

  @Autowired
  private ThreadPoolConfigMapper mapper;

  @Override
  // 保存或更新
  public ThreadPoolConfigEntity save(String applicationName,
      ThreadPoolSnapshot threadPoolSnapshot) {
    ThreadPoolConfigEntity threadPoolConfigEntity = mapper
        .selectOneByApplicationNameAndThreadPoolName(
            applicationName, threadPoolSnapshot.getThreadPoolName());
    if (threadPoolConfigEntity == null) {
      threadPoolConfigEntity = ThreadPoolConfigEntity.builder().applicationName(applicationName)
          .build();
      ThreadPoolConfigEntity result = ObjectUtils.merge(threadPoolSnapshot, threadPoolConfigEntity);
      mapper.insert(result);
    } else {
      ThreadPoolConfigEntity result = ObjectUtils.merge(threadPoolSnapshot, threadPoolConfigEntity);
      if (!ObjectUtils.daoCompareTo(result, threadPoolConfigEntity)) {
        mapper.update(result);
      } else {
        log.info("id({}) is same, do not need to update", threadPoolConfigEntity.getId());
      }
    }

    return threadPoolConfigEntity;
  }
}

package com.gcy.baiji.server.service;

import com.gcy.baiji.common.utils.ObjectUtils;
import com.gcy.baiji.common.vo.ThreadPoolSnapshot;
import com.gcy.baiji.server.entity.ThreadPoolConfigEntity;
import com.gcy.baiji.server.mapper.ThreadPoolConfigMapper;
import com.gcy.baiji.tools.cache.LruCache;
import com.gcy.baiji.tools.cache.LruEvictableCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThreadPoolConfigServiceImpl implements ThreadPoolConfigService {

  @Autowired
  private ThreadPoolConfigMapper mapper;

  private final LruCache<String, ThreadPoolConfigEntity> cache = new LruEvictableCache<>(1 << 10,
      1 << 6);

  @Override
  // 保存或更新
  public ThreadPoolConfigEntity save(String applicationName,
      ThreadPoolSnapshot threadPoolSnapshot) {
    ThreadPoolConfigEntity threadPoolConfigEntity = selectOneByApplicationNameAndThreadPoolName(
        applicationName, threadPoolSnapshot.getThreadPoolName());
    if (threadPoolConfigEntity == null) {
      threadPoolConfigEntity = ThreadPoolConfigEntity.builder().applicationName(applicationName)
          .build();
      ObjectUtils.merge(threadPoolSnapshot, threadPoolConfigEntity);
      mapper.insert(threadPoolConfigEntity);
    } else {
      ThreadPoolConfigEntity result = ObjectUtils.merge(threadPoolSnapshot, threadPoolConfigEntity);
      if (!ObjectUtils.daoCompareTo(result, threadPoolConfigEntity)) {
        mapper.update(threadPoolConfigEntity);
      } else {
        System.out
            .println("id(" + threadPoolConfigEntity.getId() + ") is same, do not need to update");
      }
    }

    return threadPoolConfigEntity;
  }

  public ThreadPoolConfigEntity selectOneByApplicationNameAndThreadPoolName(String applicationName,
      String poolName) {
    String cacheKey = applicationName + "-" + poolName;
    ThreadPoolConfigEntity threadPoolConfigEntity = cache.get(cacheKey);
    if (threadPoolConfigEntity != null) {
      return threadPoolConfigEntity;
    }
    threadPoolConfigEntity = mapper
        .selectOneByApplicationNameAndThreadPoolName(applicationName, poolName);
    cache.put(cacheKey, threadPoolConfigEntity);
    return threadPoolConfigEntity;
  }
}

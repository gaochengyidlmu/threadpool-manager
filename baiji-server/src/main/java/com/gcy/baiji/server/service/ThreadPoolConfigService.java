package com.gcy.baiji.server.service;

import com.gcy.baiji.common.vo.ThreadPoolSnapshot;
import com.gcy.baiji.server.entity.ThreadPoolConfigEntity;

public interface ThreadPoolConfigService {

  ThreadPoolConfigEntity save(String applicationName,
      ThreadPoolSnapshot threadPoolSnapshot);
}

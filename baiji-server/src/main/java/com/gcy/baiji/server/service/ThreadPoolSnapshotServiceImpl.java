package com.gcy.baiji.server.service;

import com.gcy.baiji.server.entity.ThreadPoolSnapshotEntity;
import com.gcy.baiji.server.mapper.ThreadPoolSnapshotMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThreadPoolSnapshotServiceImpl implements ThreadPoolSnapshotService {

  @Autowired
  ThreadPoolSnapshotMapper threadPoolSnapshotMapper;

  public void bulkCreate(List<ThreadPoolSnapshotEntity> list) {
    for (ThreadPoolSnapshotEntity entity : list) {
      threadPoolSnapshotMapper.insert(entity);
    }
  }
}

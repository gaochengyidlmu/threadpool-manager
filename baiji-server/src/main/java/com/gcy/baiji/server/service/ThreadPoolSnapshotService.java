package com.gcy.baiji.server.service;

import com.gcy.baiji.server.entity.ThreadPoolSnapshotEntity;
import java.util.List;

public interface ThreadPoolSnapshotService {

  void bulkCreate(List<ThreadPoolSnapshotEntity> list);
}

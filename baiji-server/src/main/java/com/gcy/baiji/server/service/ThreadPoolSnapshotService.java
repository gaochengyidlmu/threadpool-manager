package com.gcy.baiji.server.service;

import com.gcy.baiji.server.domain.ThreadPoolSnapshotDAO;
import com.gcy.baiji.server.mapper.ThreadPoolSnapshotMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThreadPoolSnapshotService {

  @Autowired
  ThreadPoolSnapshotMapper threadPoolSnapshotMapper;

  public void bulkCreate(List<ThreadPoolSnapshotDAO> list) {
    for (ThreadPoolSnapshotDAO dao : list) {
      dao.setTpId(1);
      threadPoolSnapshotMapper.insert(dao);
    }
  }
}

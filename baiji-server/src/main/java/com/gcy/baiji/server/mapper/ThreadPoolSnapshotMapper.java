package com.gcy.baiji.server.mapper;

import com.gcy.baiji.server.entity.ThreadPoolSnapshotEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ThreadPoolSnapshotMapper {

  void insert(ThreadPoolSnapshotEntity albumChild);
}

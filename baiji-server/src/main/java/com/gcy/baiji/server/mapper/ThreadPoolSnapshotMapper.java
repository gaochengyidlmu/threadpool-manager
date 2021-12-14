package com.gcy.baiji.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gcy.baiji.server.domain.ThreadPoolSnapshotDAO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ThreadPoolSnapshotMapper extends BaseMapper<ThreadPoolSnapshotDAO> {

}

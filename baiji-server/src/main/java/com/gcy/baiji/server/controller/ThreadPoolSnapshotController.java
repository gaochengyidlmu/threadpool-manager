package com.gcy.baiji.server.controller;

import com.gcy.baiji.common.http.Result;
import com.gcy.baiji.common.tool.GenericConvert;
import com.gcy.baiji.common.vo.ThreadPoolSnapshot;
import com.gcy.baiji.server.domain.ThreadPoolSnapshotDAO;
import com.gcy.baiji.server.service.ThreadPoolSnapshotService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/thread_pool_snapshots")
public class ThreadPoolSnapshotController {

  @Autowired
  ThreadPoolSnapshotService threadPoolSnapshotService;

  @PostMapping
  public Result<String> bulkCreate(@RequestBody List<ThreadPoolSnapshot> list) {
    threadPoolSnapshotService.bulkCreate(list.stream().map(item -> GenericConvert.convert(item,
        ThreadPoolSnapshotDAO.class)).collect(Collectors.toList()));
    return Result.success("success");
  }
}

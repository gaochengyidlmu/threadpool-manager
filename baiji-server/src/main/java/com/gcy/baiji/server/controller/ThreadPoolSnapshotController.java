package com.gcy.baiji.server.controller;

import static com.gcy.baiji.common.http.HttpResource.API;
import static com.gcy.baiji.common.http.HttpResource.PORT_HEADER;
import static com.gcy.baiji.common.http.HttpResource.THREAD_POOL_SNAPSHOTS;
import static com.gcy.baiji.common.http.HttpResource.V1;

import com.gcy.baiji.common.http.Result;
import com.gcy.baiji.common.utils.GenericConvert;
import com.gcy.baiji.common.vo.ThreadPoolSnapshot;
import com.gcy.baiji.server.entity.ThreadPoolConfigEntity;
import com.gcy.baiji.server.entity.ThreadPoolSnapshotEntity;
import com.gcy.baiji.server.service.ThreadPoolConfigService;
import com.gcy.baiji.server.service.ThreadPoolSnapshotService;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(API + V1 + THREAD_POOL_SNAPSHOTS)
public class ThreadPoolSnapshotController {

  @Autowired
  ThreadPoolSnapshotService threadPoolSnapshotService;

  @Autowired
  ThreadPoolConfigService threadPoolConfigService;

  @PostMapping
  public Result<String> bulkCreate(@RequestBody List<ThreadPoolSnapshot> list,
      @RequestParam String applicationName, HttpServletRequest request) {
    if (list.size() == 0) {
      return Result.success("success");
    }
    String ip = request.getHeader("x-forwarded-for") != null ? request.getHeader("x-forwarded-for")
        : request.getRemoteAddr();
    String port = request.getHeader(PORT_HEADER);

    threadPoolSnapshotService.bulkCreate(
        list.stream().map(item -> {
          ThreadPoolConfigEntity threadPoolConfigEntity = threadPoolConfigService
              .save(applicationName, item);
          ThreadPoolSnapshotEntity entity = GenericConvert
              .convert(item, ThreadPoolSnapshotEntity.class);
          entity.setTpConfigId(threadPoolConfigEntity.getId());
          entity.setIp(ip);
          entity.setPort(port);
          return entity;
        }).collect(Collectors.toList())
    );
    return Result.success("success");
  }
}

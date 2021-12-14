package com.gcy.baiji.client.report;

import com.gcy.baiji.common.http.Result;
import com.gcy.baiji.common.vo.ThreadPoolSnapshot;
import java.util.List;

public class HttpSnapshotReporter implements SnapshotReporter<Result<String>> {

  public final Result<String> report(ThreadPoolSnapshot snapshot) {
    return doReport(snapshot);
  }

  protected Result<String> doReport(ThreadPoolSnapshot snapshot) {
    return request(List.of(snapshot));
  }

  public Result<String> request(List<ThreadPoolSnapshot> snapshots) {
    // TODO: 实现通过 http 与 server 端的交互
    return Result.success("success");
  }
}

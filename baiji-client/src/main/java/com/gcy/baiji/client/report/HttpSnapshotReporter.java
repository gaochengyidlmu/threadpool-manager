package com.gcy.baiji.client.report;

import com.gcy.baiji.common.client.ThreadPoolSnapshotClient;
import com.gcy.baiji.common.http.Result;
import com.gcy.baiji.common.vo.ThreadPoolSnapshot;
import java.util.List;

public class HttpSnapshotReporter implements SnapshotReporter<Result<String>> {

  private final ThreadPoolSnapshotClient client;

  public HttpSnapshotReporter(ThreadPoolSnapshotClient client) {
    this.client = client;
  }

  public final Result<String> report(ThreadPoolSnapshot snapshot) {
    return doReport(snapshot);
  }

  protected Result<String> doReport(ThreadPoolSnapshot snapshot) {
    return request(List.of(snapshot));
  }

  public Result<String> request(List<ThreadPoolSnapshot> snapshots) {
    return client.bulkCreate(snapshots);
  }
}

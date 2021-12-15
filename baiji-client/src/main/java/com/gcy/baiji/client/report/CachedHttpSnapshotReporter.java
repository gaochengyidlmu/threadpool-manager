package com.gcy.baiji.client.report;

import com.gcy.baiji.common.client.ThreadPoolSnapshotClient;
import com.gcy.baiji.common.http.Result;
import com.gcy.baiji.common.vo.ThreadPoolSnapshot;
import java.util.ArrayList;
import java.util.List;

public class CachedHttpSnapshotReporter extends HttpSnapshotReporter {

  private final static int flushCapacity = 12;
  private final List<ThreadPoolSnapshot> list = new ArrayList<>(16);

  public CachedHttpSnapshotReporter(ThreadPoolSnapshotClient client) {
    super(client);
  }

  @Override
  protected Result<String> doReport(ThreadPoolSnapshot snapshot) {
    list.add(snapshot);
    if (list.size() < flushCapacity) {
      return Result.success("success");
    }
    Result<String> result = super.request(list);
    list.clear();
    return result;
  }
}

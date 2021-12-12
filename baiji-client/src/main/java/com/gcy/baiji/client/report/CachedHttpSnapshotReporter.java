package com.gcy.baiji.client.report;

import com.gcy.baiji.common.core.ThreadPoolSnapshot;
import com.gcy.baiji.common.http.Result;
import java.util.ArrayList;
import java.util.List;

public class CachedHttpSnapshotReporter extends HttpSnapshotReporter {

  private final static int flushCapacity = 12;
  private final List<ThreadPoolSnapshot> list = new ArrayList<>(16);

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

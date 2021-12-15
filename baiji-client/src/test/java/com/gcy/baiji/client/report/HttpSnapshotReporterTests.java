package com.gcy.baiji.client.report;

import com.gcy.baiji.common.client.ThreadPoolSnapshotClient;
import com.gcy.baiji.common.client.impl.ThreadPoolSnapshotClientImpl;
import com.gcy.baiji.common.vo.ThreadPoolSnapshot;

public class HttpSnapshotReporterTests {

  public static void main(String[] args) {
    ThreadPoolSnapshotClient client = new ThreadPoolSnapshotClientImpl("http://localhost:8080");
    HttpSnapshotReporter reporter = new HttpSnapshotReporter(client);
    System.out.println(reporter.report(
        ThreadPoolSnapshot.Builder().activeThreadNum(1).corePoolSize(5).idleThreadNum(11)
            .currentTaskCount(23).build()));
  }
}

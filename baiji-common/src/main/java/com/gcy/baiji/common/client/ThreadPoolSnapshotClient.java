package com.gcy.baiji.common.client;

import com.gcy.baiji.common.http.Result;
import com.gcy.baiji.common.vo.ThreadPoolSnapshot;
import java.util.List;

public interface ThreadPoolSnapshotClient {

  Result<String> bulkCreate(String applicationName, List<ThreadPoolSnapshot> list);
}

package com.gcy.baiji.common.client.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gcy.baiji.common.client.ThreadPoolSnapshotClient;
import com.gcy.baiji.common.http.OkHttpClientFactory;
import com.gcy.baiji.common.http.Result;
import com.gcy.baiji.common.tool.ObjectMapperFactory;
import com.gcy.baiji.common.vo.ThreadPoolSnapshot;
import java.io.IOException;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ThreadPoolSnapshotClientImpl implements ThreadPoolSnapshotClient {

  private final String serverHost;
  private final ObjectMapper objectMapper = ObjectMapperFactory.createObjectMapper();
  public static final MediaType JSON
      = MediaType.get("application/json; charset=utf-8");

  public ThreadPoolSnapshotClientImpl(String serverHost) {
    this.serverHost = serverHost;
  }

  @Override
  public Result<String> bulkCreate(List<ThreadPoolSnapshot> list) {
    OkHttpClient client = OkHttpClientFactory.getInstance();

    Request request = null;
    try {
      request = new Request.Builder()
          .url(serverHost + "/api/v1/thread_pool_snapshots")
          .post(RequestBody.create(JSON, objectMapper.writeValueAsString(list)))
          .build();
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    Result<String> result = null;
    try (Response response = client.newCall(request).execute()) {
      if (response.code() == 200) {
        result = objectMapper.readValue(response.body().string(), Result.class);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }
}

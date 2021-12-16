package com.gcy.baiji.common.client.impl;

import static com.gcy.baiji.common.http.HttpResource.API;
import static com.gcy.baiji.common.http.HttpResource.PORT_HEADER;
import static com.gcy.baiji.common.http.HttpResource.THREAD_POOL_SNAPSHOTS;
import static com.gcy.baiji.common.http.HttpResource.V1;

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
  private final String clientPort;
  private final ObjectMapper objectMapper = ObjectMapperFactory.createObjectMapper();
  public static final MediaType JSON
      = MediaType.get("application/json; charset=utf-8");

  public ThreadPoolSnapshotClientImpl(String serverHost, String clientPort) {
    this.serverHost = serverHost;
    this.clientPort = clientPort;
  }

  @Override
  public Result<String> bulkCreate(String applicationName, List<ThreadPoolSnapshot> list) {
    OkHttpClient client = OkHttpClientFactory.getInstance();

    Request request = null;
    try {
      request = new Request.Builder()
          .url(
              serverHost + API + V1 + THREAD_POOL_SNAPSHOTS + "?applicationName=" + applicationName)
          .post(RequestBody.create(JSON, objectMapper.writeValueAsString(list)))
          .addHeader(PORT_HEADER, clientPort)
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

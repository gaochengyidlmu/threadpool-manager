package com.gcy.baiji.common.http;

import okhttp3.OkHttpClient;

public class OkHttpClientFactory {

  private static OkHttpClient client;

  public static OkHttpClient getInstance() {
    if (client != null) {
      return client;
    }

    synchronized (OkHttpClientFactory.class) {
      if (client == null) {
        client = new OkHttpClient();
      }
      return client;
    }
  }

}

package com.gcy.baiji.client.report;

public interface Reporter<V, R> {

  R report(V v);
}

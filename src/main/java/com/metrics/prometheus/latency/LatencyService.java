package com.metrics.prometheus.latency;

import com.google.inject.Singleton;
import io.prometheus.client.Histogram;

@Singleton
public class LatencyService {

  private final Histogram latencyHistogram = Histogram.build()
      .name("api_request_latency_millis")
      .help("Request latency in milliseconds")
      .labelNames("method", "endpoint")
      .buckets(0.5D, 1D, 2D, 3D, 4D, 5D)
      .register();


  public Histogram.Timer startTimerWithLabelValues(MethodValues method, String endPoint) {
    return this.latencyHistogram.labels(method.toString(), endPoint).startTimer();
  }
}

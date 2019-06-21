package com.metrics.prometheus.latency;

import com.google.inject.Singleton;
import io.prometheus.client.Histogram;

@Singleton
public class LatencyService {

  private final Histogram latencyHistogram;

  private static final String API_REQUEST_LATENCY_MILLIS = "api_request_latency_millis";
  private static final String REQUEST_LATENCY_IN_MILLISECONDS = "Request latency in milliseconds";
  private static final String METHOD = "method";
  
  public LatencyService() {

    this.latencyHistogram = Histogram.build()
        .name(API_REQUEST_LATENCY_MILLIS)
        .help(REQUEST_LATENCY_IN_MILLISECONDS)
        .labelNames(METHOD)
        .buckets(0.5D, 1D, 2D, 3D, 4D)
        .register();
  }


  public Histogram.Timer startTimerWithLabelValues(MethodValues method) {
    return this.latencyHistogram.labels(method.toString()).startTimer();
  }

  public Histogram getLatencyHistogram() {
    return latencyHistogram;
  }

}

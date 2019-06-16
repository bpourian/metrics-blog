package com.metrics.prometheus.latency;

import com.google.inject.Singleton;
import io.prometheus.client.Collector;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Histogram;

@Singleton
public class LatencyService {

  private final Histogram latencyHistogram;
  private final CollectorRegistry registry;

  public static final String API_REQUEST_LATENCY_MILLIS = "api_request_latency_millis";
  public static final String REQUEST_LATENCY_IN_MILLISECONDS = "Request latency in milliseconds";
  public static final String METHOD = "method";
  public static final String ENDPOINT = "endpoint";


  public LatencyService() {

    this.registry = new CollectorRegistry();

    this.latencyHistogram = Histogram.build()
        .name(API_REQUEST_LATENCY_MILLIS)
        .help(REQUEST_LATENCY_IN_MILLISECONDS)
        .labelNames(METHOD)
        .buckets(0.25D, 0.5D, 1D, 2D)
        .register(registry);

  }


  public Histogram.Timer startTimerWithLabelValues(MethodValues method) {
    return this.latencyHistogram.labels(method.toString()).startTimer();
  }

  public Histogram getLatencyHistogram() {
    return latencyHistogram;
  }

  public CollectorRegistry getRegistry() {
    return registry;
  }

  public double getCount(MethodValues method) {
    return registry.getSampleValue(method.toString());
  }

  public double getSum(MethodValues method) {
    return registry.getSampleValue(method.toString());
  }

  public double getBucket(double b, String labelName) {
    return registry.getSampleValue(labelName,
        new String[]{"le"},
        new String[]{Collector.doubleToGoString(b)});
  }
}

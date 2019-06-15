package com.metrics.prometheus.foundation;

import com.google.inject.Singleton;
import io.prometheus.client.Histogram;
import java.util.List;

@Singleton
public class Publisher {

  private final Histogram histogram;

  public Publisher(
      String name,
      String description,
      List<String> labels,
      List<Double> bucketsInSeconds
  ) {
    histogram = Histogram.build()
        .name(name)
        .help(description)
        .labelNames(labels.toArray(new String[labels.size()]))
        .buckets(buckets(bucketsInSeconds))
        .register();
  }


  private double[] buckets(List<Double> bucketsInSeconds) {

    final double buckets[] = new double[bucketsInSeconds.size()];

    for (int i = 0; i < bucketsInSeconds.size(); i++) {
      buckets[i] = bucketsInSeconds.get(i);
    }
    return buckets;
  }

}

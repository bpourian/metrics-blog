package com.metrics.prometheus.latency;

import static org.assertj.core.api.Assertions.assertThat;

import io.prometheus.client.Collector;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Histogram;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LatencyServiceTest {

  private LatencyService underTest;
  private CollectorRegistry registry;

  @BeforeEach
  void setUp() {
    underTest = new LatencyService();
  }

  @Test
  void buildTimer() {
    // Given
    Histogram.Timer getMethodTimer = underTest.startTimerWithLabelValues(MethodValues.GET);
    Histogram.Timer postMethodTimer = underTest.startTimerWithLabelValues(MethodValues.POST);

    // When

    List<Integer> sleep = Arrays.asList(200, 450, 900, 1800, 3000);

    sleep.forEach(milliseconds -> {

      try {
        Thread.sleep(milliseconds);
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        getMethodTimer.observeDuration();
        postMethodTimer.observeDuration();
      }
    });

    // Then

    underTest.getRegistry();
//    System.out.println(underTest.getCount(MethodValues.GET));
//    System.out.println(underTest.getCount(MethodValues.POST));

    System.out.println(getMethodTimer);
    System.out.println(postMethodTimer);
    System.out.println(underTest.getLatencyHistogram());
    assertThat(underTest.getLatencyHistogram().collect().get(0)).isEqualTo(null);
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

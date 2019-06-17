package com.metrics.prometheus.latency;

import static org.assertj.core.api.Assertions.assertThat;

import io.prometheus.client.Histogram;
import io.prometheus.client.Histogram.Timer;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LatencyServiceTest {

  private LatencyService underTest;

  @BeforeEach
  void setUp() {
    underTest = new LatencyService();
  }

  @Test
  void shouldObserveDurationData() {
    // given

    // when
    List<Integer> sleep = Arrays.asList(1000, 2000, 3000, 4000);
    Timer getMethodTimer = underTest.startTimerWithLabelValues(MethodValues.GET);

    sleep.forEach(milliseconds -> {
      try {
        Thread.sleep(milliseconds);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      underTest.getLatencyHistogram().observe(getMethodTimer.observeDuration());

    });
    double actualValue = underTest.getLatencyHistogram().collect().get(0).samples.get(4).value;
    String actualString = underTest.getLatencyHistogram().collect().get(0).samples.get(4).labelValues.get(0);

    assertThat(actualValue).isEqualTo(2.0D);
    assertThat(actualValue).isNotEqualTo(1.0D);

    assertThat(actualString).isEqualTo("GET");
    assertThat(actualString).isNotEqualTo("");

  }

  //      System.out.println(getMethodTimer.observeDuration());
//      System.out.println(myService.getLatencyHistogram().collect().get(0).toString());
//      System.out.println(underTest.getLatencyHistogram().collect().get(0).samples.get(3));
//      System.out.println(underTest.getLatencyHistogram().collect().get(0).samples.get(3).labelValues);
//      System.out.println(underTest.getLatencyHistogram().collect().get(0).samples.get(3).value);
//
//      System.out.println(underTest.getLatencyHistogram().collect().get(0).samples.get(4));
//      System.out.println(underTest.getLatencyHistogram().collect().get(0).samples.get(4).labelValues);
//      System.out.println(underTest.getLatencyHistogram().collect().get(0).samples.get(4).value);
}

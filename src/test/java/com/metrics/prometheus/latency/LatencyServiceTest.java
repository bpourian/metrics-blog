package com.metrics.prometheus.latency;

import static org.assertj.core.api.Assertions.assertThat;

import io.prometheus.client.Histogram;
import io.prometheus.client.Histogram.Timer;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class LatencyServiceTest {

  private LatencyService underTest;

  @Test
  void shouldObserveDurationData() {
    // given
    underTest = new LatencyService();

    List<Integer> sleep = Arrays.asList(1000, 2000, 3000, 3000);

    // when
    sleep.forEach(milliseconds -> {
      Histogram.Timer getMethodTimer = underTest.startTimerWithLabelValues(MethodValues.GET);

      try {
        Thread.sleep(milliseconds);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println(getMethodTimer.observeDuration());

    });

    // then
    assertThat(underTest.getLatencyHistogram().collect().get(0).samples.get(3).value).isEqualTo(1);
//    assertBucketValueIsEqualToExpected(0.5, 0.0);
//    assertBucketValueIsEqualToExpected(1.0, 0.0);
//    assertBucketValueIsEqualToExpected(2.0, 1.0);
//    assertBucketValueIsEqualToExpected(3.0, 1.0);
//    assertBucketValueIsEqualToExpected(4.0, 2.0);
  }

//  private void assertBucketValueIsEqualToExpected(double bucket, double expected) {
//    underTest.getLatencyHistogram().collect().get(0).samples
//        .forEach(item -> {
//          System.out.println(item);
//          if (item.labelValues.contains(Double.toString(bucket))) {
//            assertThat(item.value).isEqualTo(expected);
//          }
//        });
//    System.out.println(underTest.getLatencyHistogram().collect());
//
//  }
}

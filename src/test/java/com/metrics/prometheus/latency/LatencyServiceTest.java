package com.metrics.prometheus.latency;

import io.prometheus.client.Histogram;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LatencyServiceTest {

  // under test
  private LatencyService underTest;

  @BeforeEach
  void setUp() {
    underTest = new LatencyService();
  }

  @Test
  void buildTimer() {
    // Given
    Histogram.Timer getMethodTimer = underTest
        .startTimerWithLabelValues(MethodValues.GET, "http://localhost:9090/api/v1/happy");

    Histogram.Timer postMethodTimer = underTest
        .startTimerWithLabelValues(MethodValues.POST, "http://localhost:9090/api/v1/happy");

    // When
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      getMethodTimer.observeDuration();
      postMethodTimer.observeDuration();
    }

    // Then
    System.out.println(getMethodTimer);
    System.out.println(postMethodTimer);
    System.out.println(underTest);
  }
}

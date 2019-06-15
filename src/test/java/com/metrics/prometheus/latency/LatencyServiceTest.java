package com.metrics.prometheus.latency;

import io.prometheus.client.Histogram;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PublisherTest {

  // under test
  private LatencyService underTest;

  @BeforeEach
  void setUp() {
    underTest = new LatencyService();
  }

  @Test
  void buildTimer() {
    // Given
    Histogram.Timer myTimer = underTest
        .startTimerWithLabelValues(MethodValues.GET, "http://localhost:9090/api/v1/happy");

    // When
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      myTimer.observeDuration();
    }

    // Then
    System.out.println(myTimer);
    System.out.println(underTest);
  }
}

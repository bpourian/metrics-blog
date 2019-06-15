package com.metrics.prometheus.foundation;

import com.google.inject.Guice;
import com.google.inject.Inject;
import io.prometheus.client.Histogram;
import io.prometheus.client.Histogram.Builder;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PublisherTest {

  // under test
  private Publisher underTest;

  // test constants
  String name = "my_latency_test";
  String description = "to_help_measure_latency";
  List<String> labels = Arrays.asList("latency_measure");
  List<Double> buckets = Arrays.asList(0.5, 1.0, 2.0, 3.0, 4.0, 5.0);

//  @BeforeEach
//  void setUp() {
//
//    underTest = new Publisher();
//
//  }

  @Test
  void buildTimer() {

    // Given
    underTest = new Publisher(name, description, labels, buckets);

    // When


    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
//      myTimer.observeDuration();
    }

    // Then
    System.out.println(underTest);
//    System.out.println(myTimer);
  }
}

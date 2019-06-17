package com.metrics.prometheus.latency;

import io.prometheus.client.Collector.MetricFamilySamples;
import io.prometheus.client.Histogram;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LatencyServiceTest {

  private LatencyService underTest;
//  private CollectorRegistry registry;

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

    List<Integer> sleep = Arrays.asList(200, 200, 200, 450,450,450, 900, 1800, 3000);

    sleep.forEach(milliseconds -> {



      try {
        underTest.startTimerWithLabelValues(MethodValues.GET);
        underTest.startTimerWithLabelValues(MethodValues.POST);
        Thread.sleep(milliseconds);
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        System.out.println(getMethodTimer.observeDuration());
        postMethodTimer.observeDuration();
      }
    });

    // Then

//    Enumeration<MetricFamilySamples> sample = underTest.getRegistry().metricFamilySamples();
//    List<MetricFamilySamples> sample2 = underTest.getLatencyHistogram().collect();


    underTest.getLatencyHistogram().collect().get(0).samples.forEach(t -> {
      System.out.println(t);
      System.out.println("--------------");
    } );




//    System.out.println(underTest.getRegistry().getSampleValue("api_request_latency_millis"));

//    System.out.println(getMethodTimer);
//    System.out.println(postMethodTimer);
    System.out.println(underTest.getLatencyHistogram());
//    assertThat(underTest.getLatencyHistogram().collect().get(0)).isEqualTo(null);
  }

  @Test
  void shouldObserveDurationData() {
    // given


    // when


    // then
    Histogram.Timer apiTimer = publisher.apiCallTimer(ApiName.QUOTE);

//    apiTimer.observeDuration();

    publisher.getCurrencyCloudInvocationDuration().collect().get(0).samples.forEach(i -> {
      if (i.labelValues.contains("0.25")) {

        System.out.println(i.labelValues);
      }


  }

}

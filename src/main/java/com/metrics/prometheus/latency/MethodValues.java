package com.metrics.prometheus.latency;

public enum MethodValues {
  GET,
  POST;

  @Override
  public String toString() {
    return this.name().toUpperCase();
  }
}

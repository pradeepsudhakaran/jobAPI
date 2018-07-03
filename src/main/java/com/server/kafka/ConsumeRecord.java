package com.server.kafka;

import java.util.Properties;

public class ConsumeRecord {
  static Properties properties;

  public ConsumeRecord(String url) {
    properties = new Properties();
    properties.put("bootstrap.servers", url);
    properties.put("group.id", 0);
    properties.put("enable.auto.commit", "true");
    properties.put("auto.commit.interval.ms", "1000");
    properties.put("session.timeout.ms", "30000");
    properties.put("key.deserializer",
        "org.apache.kafka.common.serializa-tion.StringDeserializer");
    properties.put("value.deserializer",
        "org.apache.kafka.common.serializa-tion.StringDeserializer");
  }

  public static Properties getProperties() {
    return properties;
  }
}

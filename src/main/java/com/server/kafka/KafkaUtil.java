package com.server.kafka;

import java.util.Properties;

public class KafkaUtil {

  static Properties properties;

  public KafkaUtil(String url){
    properties = new Properties();
    properties.put("bootstrap.servers", url);
    properties.put("acks", "all");
    properties.put("retries", 0);
    properties.put("batch.size", 16384);
    properties.put("linger.ms", 1);
    properties.put("buffer.memory", 33554432);
    properties.put("key.serializer",
        "org.apache.kafka.common.serializa-tion.StringSerializer");
    properties.put("value.serializer",
        "org.apache.kafka.common.serializa-tion.StringSerializer");
  }

  public static Properties getProperties() {
    return properties;
  }
}


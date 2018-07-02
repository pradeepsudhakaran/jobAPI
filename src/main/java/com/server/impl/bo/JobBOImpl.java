package com.server.impl.bo;


import com.server.bo.JobBO;
import com.server.dao.JobDAO;
import com.server.impl.dao.JobDAOImpl;

import com.server.kafka.KafkaUtil;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class JobBOImpl implements JobBO {

  @Override
  public JsonArray getJobList(String searchFilter) throws SQLException {
    JobDAO jobDAO = new JobDAOImpl();
    JsonArray resultArray = jobDAO.getJobsList(searchFilter);

  return resultArray;
  }

  @Override
  public int createJob(MultiMap params) {
    int count = 0;

    KafkaUtil kafkaUtil = new KafkaUtil("localhost:9092");
    Properties properties = KafkaUtil.getProperties();

    String topicName = "Job";

    Producer<String, String> producer = new KafkaProducer<String, String>(properties);

    for (String paramName : params.names()) {
      producer.send(new ProducerRecord<String, String>(topicName, paramName, params.get(paramName)));
      count++;
    }

    producer.close();

    return count;
  }
}

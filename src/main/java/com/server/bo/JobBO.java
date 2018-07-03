package com.server.bo;

import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonArray;

import java.sql.SQLException;

public interface JobBO {
  JsonArray getJobList(String searchFilter) throws SQLException;
  int createJob(MultiMap params);
  int consumeRecord() throws SQLException;
}

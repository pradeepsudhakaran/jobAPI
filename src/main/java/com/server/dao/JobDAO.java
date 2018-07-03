package com.server.dao;


import io.vertx.core.json.JsonArray;

import java.sql.SQLException;
import java.util.HashMap;

public interface JobDAO {
  JsonArray getJobsList(String searchFilter) throws SQLException;
  int insertRecord(HashMap<String, String> parameters) throws SQLException;
}

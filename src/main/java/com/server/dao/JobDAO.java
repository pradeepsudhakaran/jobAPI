package com.server.dao;


import io.vertx.core.json.JsonArray;

import java.sql.SQLException;

public interface JobDAO {
  JsonArray getJobsList(String searchFilter) throws SQLException;
}

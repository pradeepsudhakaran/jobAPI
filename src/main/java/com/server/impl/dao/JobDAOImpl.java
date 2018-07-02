package com.server.impl.dao;

import com.server.dao.JobDAO;
import com.server.helper.HandleConnection;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JobDAOImpl implements JobDAO {

  @Override
  public JsonArray getJobsList(String searchFilter)  throws SQLException {
    HandleConnection handleConnection = new HandleConnection();
    Connection connection = handleConnection.checkAndEstablishConnection(false);
    StringBuilder selectQuery = new StringBuilder();
    selectQuery.append("select title,job_description,skills,experience_level,countries,pay_rate,languages,location,availability,job_type from Job where ");
    selectQuery.append("(UPPER(Job.skills) like ? or UPPER(\"");
    selectQuery.append("Job.experience_level) like ? ");
    selectQuery.append(" or UPPER(Job.countries) like ? ");
    selectQuery.append(" or UPPER(Job.availability) like ? ");
    selectQuery.append(" or UPPER(Job.job_type) like ? ");
    selectQuery.append(" or (UPPER(Job.pay_rate) like ? )");

    PreparedStatement preparedStatement = connection.prepareStatement(selectQuery.toString());
    int index = 0;
    preparedStatement.setString(++index, searchFilter);
    preparedStatement.setString(++index, searchFilter);
    preparedStatement.setString(++index, searchFilter);
    preparedStatement.setString(++index, searchFilter);
    preparedStatement.setString(++index, searchFilter);
    preparedStatement.setString(++index, searchFilter);

    ResultSet resultSet = preparedStatement.executeQuery();

    JsonArray resultArray = new JsonArray();

    while (resultSet.next()) {
      JsonObject jsonObject = new JsonObject();
      jsonObject.put("title", resultSet.getString(1));
      jsonObject.put("job_description", resultSet.getString(2));
      jsonObject.put("skills", resultSet.getString(3));
      jsonObject.put("experience_level", resultSet.getString(4));
      jsonObject.put("countries", resultSet.getString(5));
      jsonObject.put("pay_rate", resultSet.getString(6));
      jsonObject.put("languages", resultSet.getString(7));
      jsonObject.put("location", resultSet.getString(8));
      jsonObject.put("availability", resultSet.getString(9));
      jsonObject.put("job_type", resultSet.getString(10));
      resultArray.add(jsonObject);
    }

    if (!resultSet.isClosed()) {
      resultSet.close();
    }

    if (!preparedStatement.isClosed()) {
      preparedStatement.close();
    }

    if (connection.isClosed()) {
      connection.close();
    }

   return resultArray;
  }
}

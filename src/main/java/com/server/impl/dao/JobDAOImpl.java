package com.server.impl.dao;


import com.mysql.cj.core.util.StringUtils;
import com.server.dao.JobDAO;
import com.server.helper.HandleConnection;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class JobDAOImpl implements JobDAO {

  @Override
  public JsonArray getJobsList(String searchFilter)  throws SQLException {
    HandleConnection handleConnection = new HandleConnection();
    Connection connection = handleConnection.checkAndEstablishConnection(false);
    StringBuilder selectQuery = new StringBuilder();
    selectQuery.append("select id,title,job_description,skills,experience_level,countries,pay_rate,languages,location,availability,job_type from Job where ");
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
      jsonObject.put("id", resultSet.getLong(1));
      jsonObject.put("title", resultSet.getString(2));
      jsonObject.put("job_description", resultSet.getString(3));
      jsonObject.put("skills", resultSet.getString(4));
      jsonObject.put("experience_level", resultSet.getString(5));
      jsonObject.put("countries", resultSet.getString(6));
      jsonObject.put("pay_rate", resultSet.getString(7));
      jsonObject.put("languages", resultSet.getString(8));
      jsonObject.put("location", resultSet.getString(9));
      jsonObject.put("availability", resultSet.getString(10));
      jsonObject.put("job_type", resultSet.getString(11));
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

  @Override
  public int insertRecord(HashMap<String, String> parameters) throws SQLException {
    HandleConnection handleConnection = new HandleConnection();
    Connection connection = handleConnection.checkAndEstablishConnection(false);
    StringBuilder insertQuery = new StringBuilder();

    insertQuery.append("insert into Job (title, job_description, skills, experience_level, countries, pay_rate, languages" +
        ", location, availability, job_type) values (?,?,?,?,?,?,?,?,?,?)");
    PreparedStatement preparedStatement = connection.prepareStatement(insertQuery.toString());
    int index = 0;
    preparedStatement.setString(++index, StringUtils.isNullOrEmpty(parameters.get("title")) ? "" : parameters.get("title"));
    preparedStatement.setString(++index, StringUtils.isNullOrEmpty(parameters.get("job_description")) ? "" : parameters.get("job_description"));
    preparedStatement.setString(++index, StringUtils.isNullOrEmpty(parameters.get("skills")) ? "" : parameters.get("skills"));
    preparedStatement.setString(++index, StringUtils.isNullOrEmpty(parameters.get("experience_level")) ? "" : parameters.get("experience_level"));
    preparedStatement.setString(++index, StringUtils.isNullOrEmpty(parameters.get("countries")) ? "" : parameters.get("countries"));
    preparedStatement.setString(++index, StringUtils.isNullOrEmpty(parameters.get("pay_rate")) ? "" : parameters.get("pay_rate"));
    preparedStatement.setString(++index, StringUtils.isNullOrEmpty(parameters.get("languages")) ? "" : parameters.get("languages"));
    preparedStatement.setString(++index, StringUtils.isNullOrEmpty(parameters.get("location")) ? "" : parameters.get("location"));
    preparedStatement.setString(++index, StringUtils.isNullOrEmpty(parameters.get("location")) ? "" : parameters.get("location"));
    preparedStatement.setString(++index, StringUtils.isNullOrEmpty(parameters.get("availability")) ? "" : parameters.get("availability"));
    preparedStatement.setString(++index, StringUtils.isNullOrEmpty(parameters.get("job_type")) ? "" : parameters.get("job_type"));

    int count = 0;

    count = preparedStatement.executeUpdate();

    if (!preparedStatement.isClosed()) {
      preparedStatement.close();
    }

    if (connection.isClosed()) {
      connection.close();
    }

    return count;
  }
}

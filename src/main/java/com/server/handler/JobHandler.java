package com.server.handler;

import com.mysql.cj.core.util.StringUtils;
import com.server.Util.ResponseUtil;
import com.server.bo.JobBO;
import com.server.builder.ResponseBuilder;
import com.server.impl.bo.JobBOImpl;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.sql.SQLException;

public class JobHandler {
  public static void getJobList(RoutingContext routingContext) {
    HttpServerRequest request = routingContext.request();
    HttpServerResponse response = routingContext.response();
    MultiMap params = request.params();

    try {
      String searchFilter = params.get("searchFilter");

      if (StringUtils.isEmptyOrWhitespaceOnly(searchFilter)) {
        ResponseUtil.writeErrorObject(400, "filter cannot be null or empty",
            "filter cannot be null or empty", response);
      }

      JobBO jobBO = new JobBOImpl();

      JsonArray responseObject = jobBO.getJobList(searchFilter);

      ResponseUtil.writeSuccessObject(200, responseObject, response);

    } catch (SQLException e) {
      ResponseUtil.writeErrorObject(400, e.getMessage(), e.getMessage(), response);
    }
  }

  public static void createJob(RoutingContext routingContext) {
    HttpServerRequest request = routingContext.request();
    HttpServerResponse response = routingContext.response();
    MultiMap params = request.params();


    String title = params.get("title");
    String jobDescritpion = params.get("job_description");

    if (StringUtils.isEmptyOrWhitespaceOnly(title)) {
      ResponseUtil.writeErrorObject(400, "title cannot be null or empty",
          "title cannot be null or empty", response);
    }

    if (StringUtils.isEmptyOrWhitespaceOnly(jobDescritpion)) {
      ResponseUtil.writeErrorObject(400, "Description cannot be null or empty",
          "Description cannot be null or empty", response);
    }


    JobBO jobBO = new JobBOImpl();
    int jobCount = jobBO.createJob(params);

    JsonObject responseObject = new JsonObject();
    responseObject.put("message", "Job Posted");

    if (jobCount > 0) {
      ResponseUtil.writeSuccessObject(201, responseObject, response);
    } else {
      ResponseUtil.writeErrorObject(500, "Internal Server Error",
          "Internal Server Error", response);
    }
  }
}

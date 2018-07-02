package com.server.Util;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;

public class ResponseUtil {

  public static void writeSuccessObject(int code, Object dataObject, HttpServerResponse response){
    JsonObject jsonResponseObject = new JsonObject();
    jsonResponseObject.put("data", dataObject);

    response
        .setStatusCode(code)
        .putHeader("Content-Type", "application/json; charset=utf-8;")
        .end(jsonResponseObject.toString());


  }

  public static void writeErrorObject(int code,String message,String description,HttpServerResponse response){
    JsonObject errorObject = new JsonObject();

    errorObject.put("message", message);
    errorObject.put("description", description);

    response
        .setStatusCode(code)
        .putHeader("Content-Type", "application/json; charset=utf-8")
        .end(errorObject.toString());
  }
}

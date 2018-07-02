package com.server;

import com.server.helper.ConfigJSON;
import com.server.helper.Deploy;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;

public class HttpServer extends AbstractVerticle {
  private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);

  public static void main(String args[]) {


    Vertx vertx = Vertx.vertx();
    int threadInstances = 1;
    try {
      vertx.deployVerticle(Deploy.class.getName(), new DeploymentOptions().setInstances(threadInstances)
          .setConfig(ConfigJSON.getConf("config.json", "db.json", "db_secrets.json")), atsAsyncResult -> {
        if (!atsAsyncResult.succeeded()) {
          logger.error("Deployment Failure");
        } else {
          logger.info("Deployed Successfully");
        }
      });
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } finally {

    }
  }
}
package com.server.helper;

import com.server.builder.DbBuilder;
import com.server.builder.RouteBuilder;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Deploy extends AbstractVerticle {

  private static final Logger logger = LoggerFactory.getLogger(Deploy.class);

  @Override
  public void start(Future<Void> fut) throws IllegalAccessException {
    // Initialize db
    DbBuilder.initializeAtsSqlDb();

    // Create a router object
    Router router = Router.router(vertx);
    RouteBuilder.addExceptionHandlers(router);
    RouteBuilder.addRequestRouteHandlers(router);

    // Create the HTTP server and pass the "accept" method to the request handler.
    vertx
        .exceptionHandler(throwable -> {
          if (null != throwable.getCause()) {
            logger.error("Uncaught Failure", throwable.getCause());
          } else {
            logger.error("Uncaught Failure", throwable);
          }
        })
        .createHttpServer()
        .requestHandler(router::accept)
        .listen(
            // Retrieve the port from the configuration,
            this.config().getInteger("http.port"),

            result -> {
              if (result.succeeded()) {
                fut.complete();
              } else {
                fut.fail(result.cause());
              }
            }
        );
  }
}

package com.server.builder;

import com.server.handler.JobHandler;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RouteBuilder {
  private static final Logger logger = LoggerFactory.getLogger(RouteBuilder.class);

  public static void addExceptionHandlers(Router router) {
    // catches the route exception and returns ISE
    router.route().failureHandler(ctx -> {
      if (null != ctx.failure().getCause()) {
        logger.error("Route Failure", ctx.failure().getCause());
      } else {
        logger.error("Route Failure", ctx.failure());
      }

      ResponseBuilder.writeInternalServerError(ctx.response());
    });

    // catches any exception in failure handler when the response fails
    router.exceptionHandler(throwable -> {
      if (null != throwable.getCause()) {
        logger.error("Uncaught Failure", throwable.getCause());
      } else {
        logger.error("Uncaught Failure", throwable);
      }
    });
  }

  public static void addRequestRouteHandlers(Router router) {
    router.get("/job/list").produces("application/json")
        .handler(JobHandler::getJobList);
    router.route("/job*").consumes("application/x-www-form-urlencoded")
        .produces("application/json")
        .handler(BodyHandler.create().setDeleteUploadedFilesOnEnd(true));;
    router.post("/job/create").consumes("application/x-www-form-urlencoded")
        .produces("application/json")
        .handler(JobHandler::createJob);
  }
}

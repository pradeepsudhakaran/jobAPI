package com.server.builder;

import com.server.constants.ConnectionConstant;
import com.server.helper.DbConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbBuilder {
  private static final Logger logger = LoggerFactory.getLogger(DbBuilder.class);

  /**
   * Registers the JDBC driver class
   *
   * @throws ClassNotFoundException
   */
  private static void registerDriverClass() throws ClassNotFoundException {
    Class.forName(ConnectionConstant.Environment.DRIVER_CLASS_NAME);
  }

  public static boolean initializeAtsSqlDb() {
    try {
      registerDriverClass();
      DbConnectionManager.getInstance(); //.setupPool();
      return true;
    } catch (ClassNotFoundException ClassNotFoundException) {
      logger.error("db driver class not found");
      return false;
    }
  }
}

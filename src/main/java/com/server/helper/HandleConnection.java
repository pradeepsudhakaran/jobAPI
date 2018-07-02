package com.server.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class HandleConnection {

  private static Logger logger = LoggerFactory.getLogger(HandleConnection.class);

  Connection connection;
  public Connection checkAndEstablishConnection(boolean dedicated) throws
      SQLException {
    try {
      DbConnectionManager dbConnectionManager = DbConnectionManager.getInstance();
      if (dedicated) {
        this.connection = dbConnectionManager.getDedicatedConnection();
      } else {
        this.connection = dbConnectionManager.getConnectionFromPool();
      }
      this.connection.setAutoCommit(false);
    } catch (SQLException sqlException) {
      logger.error("unable to establish a connection with auto commit false/" +
          "Unable to determine the connection");

      try {
        if (null != this.connection && !this.connection.isClosed()) {
          this.connection.close();
          this.connection = null;
        }
      } catch (SQLException SqlException) {
        logger.error("Unable to close the opened connection");
      } finally {
        throw sqlException;
      }
    }
    return connection;
  }


}

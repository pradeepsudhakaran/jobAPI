package com.server.helper;

import com.server.constants.ConnectionConstant;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnectionManager {
  private static Logger logger = LoggerFactory.getLogger(DbConnectionManager.class);

  private static DbConnectionManager _instance = null;

  //private GenericObjectPool genericObjectPool;
  private String connectionUrl;
  private String user;
  private String password;
  private DataSource dataSource;
  private BasicDataSource basicDataSource;

  /**
   *
   * @param contextConfig
   *
   * @author: Pradeep S
   * @Modified: 31/JAN/2018
   */
  private DbConnectionManager(JsonObject contextConfig) {
    this.connectionUrl = contextConfig.getString(ConnectionConstant.Environment.CONNECTION_URL);
    this.user = contextConfig.getString(ConnectionConstant.Environment.USER);
    this.password = contextConfig.getString(ConnectionConstant.Environment.PASSWORD);

    // Configuring the generic pool
    this.basicDataSource = new BasicDataSource();
    this.basicDataSource.setUrl(this.connectionUrl);
    this.basicDataSource.setUsername(this.user);
    this.basicDataSource.setPassword(this.password);
    this.basicDataSource.setInitialSize(50);
    this.basicDataSource.setMaxActive(50);
    this.basicDataSource.setMaxWait(5 * 1000);
    this.basicDataSource.setDefaultAutoCommit(false);
    this.basicDataSource.setValidationQueryTimeout(30 * 1000);
    this.basicDataSource.setRemoveAbandoned(true);
    this.basicDataSource.setRemoveAbandonedTimeout(35 * 1000);
    this.dataSource = this.basicDataSource;
    /*this.genericObjectPool = new GenericObjectPool();
    this.genericObjectPool.setMinIdle(
        contextConfig.getInteger(ConnectionConstant.Environment.MAX_POOL_SIZE));
    this.genericObjectPool.setMaxActive(
        contextConfig.getInteger(ConnectionConstant.Environment.MAX_POOL_SIZE));
    this.genericObjectPool.setTimeBetweenEvictionRunsMillis(
        contextConfig.getLong(ConnectionConstant.Environment.EVICTOR_SCHEDULE_INTERVAL));
    this.genericObjectPool.setMinEvictableIdleTimeMillis(
        contextConfig.getLong(ConnectionConstant.Environment.POOL_IDLE_EVICTION_TIME));*/
  }

  /**
   * Gives the acces to instance of connection manager
   * @return DbConnection manager
   *
   * @author: Pradeep S
   * @Modified: 31/JAN/2018
   */
  public static DbConnectionManager getInstance() {
    if (_instance == null) {
      _instance = new DbConnectionManager(Vertx.currentContext().config());
    }
    return _instance;
  }

  /**
   * Sets up the pool of connection for the app
   *
   * @author: Charles Sam Dilip
   * @Modified: 31/JAN/2018
   */
  /*public void setupPool() {
    DriverManagerConnectionFactory driverManagerConnectionFactory =
        new DriverManagerConnectionFactory(this.connectionUrl, this.user, this.password);
    new PoolableConnectionFactory(driverManagerConnectionFactory, this.genericObjectPool,
            null, null, false, false);
    this.dataSource = new PoolingDataSource(this.genericObjectPool);
  }*/

  /**
   * Fetches a connection from the pool of connection established by the manager
   *
   * @return Connection to the db
   * @throws SQLException
   * @author: Charles Sam Dilip
   * @Modified: 31/JAN/2018
   */
  public Connection getConnectionFromPool() throws SQLException {
    logger.info(getPoolStatus());
    return this.dataSource.getConnection();
  }

  /**
   * Creates a new dedicated connection to the pool
   *
   * @return Connection to the db
   * @throws SQLException
   *
   * @author: Pradeep S
   * @Modified: 31/JAN/2018
   */
  public Connection getDedicatedConnection() throws SQLException {
    return DriverManager.getConnection(this.connectionUrl, this.user, this.password);
  }

  /**
   * @return String of the connection pool status
   *
   * @author: Pradeep S
   * @Modified: 31/JAN/2018
   */
  public String getPoolStatus() {
    return ConnectionConstant.MAX_POOL + this.basicDataSource.getMaxActive() + "; " +
        ConnectionConstant.ACTIVE_POOL + this.basicDataSource.getNumActive() + "; " +
        ConnectionConstant.IDLE_POOL + this.basicDataSource.getNumIdle();
  }
}

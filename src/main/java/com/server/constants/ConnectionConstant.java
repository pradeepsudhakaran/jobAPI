package com.server.constants;

public class ConnectionConstant {
  // Pool Constants
  public static final String MAX_POOL = "Max pool: ";
  public static final String ACTIVE_POOL = "Active pool: ";
  public static final String IDLE_POOL = "Idle pool: ";

  // Environment Constants
  public static class Environment {
    // Driver conf
    public static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";

    // Credentials conf
    public static final String CONNECTION_URL = "db.connectionUrl";
    public static final String USER = "db.user";
    public static final String PASSWORD = "db.pwd";

    // Pool conf
    public static final String MAX_POOL_SIZE = "db.maxPoolSize";
    public static final String EVICTOR_SCHEDULE_INTERVAL = "db.evictorScheduleInterval";
    public static final String POOL_IDLE_EVICTION_TIME = "db.poolIdleEvictionTime";
  }
}

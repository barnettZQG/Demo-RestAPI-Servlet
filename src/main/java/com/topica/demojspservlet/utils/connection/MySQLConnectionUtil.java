package com.topica.demojspservlet.utils.connection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MySQLConnectionUtil implements ConnectionUtils {

  private String JDBC_URL;
  private String username;
  private String password;

  @Override
  public Connection getConnection() {
    Connection connection = null;
    try {
      Class.forName("com.mysql.jdbc.Driver");
      this.init();
      connection = DriverManager.getConnection(this.JDBC_URL, this.username, this.password);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (Exception e) {
      throw e;
    }
    if (connection == null) {
      try {
        throw new Exception("mysql connection is null");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return connection;
  }

  private void init() {
    Properties properties = new Properties();
    InputStream input = null;

    try {
      String filename = "application.properties";
      input = getClass().getClassLoader().getResourceAsStream(filename);
      if (input == null) {
        System.out.println("Sorry, unable to find " + filename);
        return;
      }

      // load a properties file from class path, inside static method
      properties.load(input);
      if (System.getenv("MYSQL_HOST") != null) {
        this.JDBC_URL = String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=utf-8",
            System.getenv("MYSQL_HOST"), System.getenv("MYSQL_PORT"), System.getenv("MYSQL_DATABASE"));
      } else {
        this.JDBC_URL = properties.getProperty("jdbc_mysql_url");
      }
      if (System.getenv("MYSQL_USER") != null) {
        this.username = System.getenv("MYSQL_USER");
      } else {
        this.username = properties.getProperty("username");
      }
      if (System.getenv("MYSQL_PASSWORD") != null) {
        this.password = System.getenv("MYSQL_PASSWORD");
      } else {
        this.password = properties.getProperty("password");
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    } finally {
      if (input != null) {
        try {
          input.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  ;
}

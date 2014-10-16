package net.francesbagual.tdc.monads.in.practice.main

import java.sql.ResultSet
import java.sql.DriverManager
import java.sql.Connection
import java.sql.Statement

object JdbcTemplate {

  def withStatement[T](user: String, password: String)(sql: String, f: ResultSet => T): T = {
    var connection: Connection = null
    try {
      connection = DriverManager.getConnection("jdbc:myDriver:myDatabase", user, password)
      val statement: Statement = connection.createStatement();
      val resultSet: ResultSet = statement.executeQuery(sql)
      f(resultSet)
    } finally {
      if (connection != null) connection.close()
    }
  }
}


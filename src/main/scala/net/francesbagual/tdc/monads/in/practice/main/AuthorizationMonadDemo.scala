package net.francesbagual.tdc.monads.in.practice.main

import net.francesbagual.tdc.monads.in.practice.monad.Authorization
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Connection
import java.sql.Statement

object AuthorizationMonadDemo {

  def videosBaguais(username: String, password: String): Seq[String] = {
    if (username == null || password == null) return null
    var connection: Connection = null
    try {
      connection = DriverManager.getConnection("jdbc:myDriver:myDatabase", username, password)
      val statement: Statement = connection.createStatement();
      val resultSet: ResultSet = statement.executeQuery("SELECT url FROM XXX")
      var result: Seq[String] = Seq[String]()
      while (resultSet.next()) result.:+(resultSet.getString("url"))
      result
    } catch {
      case t: Throwable => throw new MyDbException(t)
    } finally {
      if (connection != null) connection.close()
    }
  }
  
  def videos(voyeur: User): Seq[String] = Authorization(voyeur) { authorizedUser =>
      videosBaguais("frances", "bagual")
    }.getOrElse(Seq[String]())
    
    
}

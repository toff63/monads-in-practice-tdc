package net.francesbagual.tdc.monads.in.practice.main

import java.sql.ResultSet
import java.sql.DriverManager
import net.francesbagual.tdc.monads.in.practice.monad.Authorization
import java.sql.Connection
import java.sql.Statement
import net.francesbagual.tdc.monads.in.practice.monad.OptionBagual
import net.francesbagual.tdc.monads.in.practice.monad.QuelqueChose

class OptionBagualDemo {

  def videosBaguais(username: OptionBagual[String],
    password: OptionBagual[String]): OptionBagual[Seq[String]] = {
    username.flatmap { user =>
      password.map { pwd =>
        var connection: Connection = null
        try {
          connection = DriverManager.getConnection("jdbc:myDriver:myDatabase", user, pwd)
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
    }
  }

  def videos(voyeur: User): Seq[String] = Authorization(voyeur) { authorizedUser =>
    videosBaguais(OptionBagual("frances"), OptionBagual("bagual")).getOrElse(Seq[String]())
  }.getOrElse(Seq[String]())

}
package net.francesbagual.tdc.monads.in.practice.main

import net.francesbagual.tdc.monads.in.practice.monad.OptionBagual
import net.francesbagual.tdc.monads.in.practice.monad.Authorization
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Connection
import java.sql.Statement
import net.francesbagual.tdc.monads.in.practice.monad.TryBagual

object TryBagualDemo {

  def videosBaguais(username: OptionBagual[String],
    password: OptionBagual[String]): OptionBagual[TryBagual[Seq[String]]] = {
    username.flatmap { user =>
      password.map { pwd =>
        TryBagual {
          var connection: Connection = null
          try {
            connection = DriverManager.getConnection("jdbc:myDriver:myDatabase", user, pwd)
            val statement: Statement = connection.createStatement();
            val resultSet: ResultSet = statement.executeQuery("SELECT url FROM XXX")
            var result: Seq[String] = Seq[String]()
            while (resultSet.next()) result.:+(resultSet.getString("url"))
            result
          } finally {
            if (connection != null) connection.close()
          }
        }
      }
    }
  }

  def videos(voyeur: User): Seq[String] = Authorization(voyeur) { authorizedUser =>
    videosBaguais(OptionBagual("frances"), OptionBagual("bagual")).map{ tryBagual => 
      tryBagual.getOrElse(Seq[String]())
    }
    .getOrElse(Seq[String]())
  }.getOrElse(Seq[String]())

}


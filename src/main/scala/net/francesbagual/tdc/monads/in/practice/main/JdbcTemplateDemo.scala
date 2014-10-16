package net.francesbagual.tdc.monads.in.practice.main

import net.francesbagual.tdc.monads.in.practice.monad.OptionBagual
import net.francesbagual.tdc.monads.in.practice.monad.Authorization
import net.francesbagual.tdc.monads.in.practice.monad.TryBagual
import java.sql.ResultSet
import java.sql.DriverManager
import java.sql.Connection
import java.sql.Statement

object JdbcTemplateDemo {

  def videosBaguais(username: OptionBagual[String],
    password: OptionBagual[String]): OptionBagual[TryBagual[Seq[String]]] =
    username.flatmap { user =>
      password.map { pwd =>
        TryBagual {
          JdbcTemplate.withStatement(user, pwd)("SELECT url FROM XXX", resultSet => {
            var result: Seq[String] = Seq[String]()
            while (resultSet.next()) result.:+(resultSet.getString("url"))
            result
          })
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


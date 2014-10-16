package net.francesbagual.tdc.monads.in.practice.monad

import net.francesbagual.tdc.monads.in.practice.main.User

object Authorization {
  def apply[T](user:User)(f: User => T):Authorization[T] = 
    if(user.age > 18) Authorized(user, f) else NotAuthorized(user)
}

trait Authorization[T] {
  def getOrElse(default:T):T = ???
}

case class Authorized[T](user:User, f: User => T) extends Authorization[T] {
    override def getOrElse(default:T):T = f(user)
}

case class NotAuthorized[T](user:User) extends Authorization[T] {
      override def getOrElse(default:T):T = default
}


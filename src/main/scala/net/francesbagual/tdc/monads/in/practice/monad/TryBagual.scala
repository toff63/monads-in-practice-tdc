package net.francesbagual.tdc.monads.in.practice.monad

object TryBagual {
  def apply[A](f: => A): TryBagual[A] =
    try (Gremio(f)) catch {
      case t: Throwable => Inter(t)
    }
}
trait TryBagual[+A] {
  def map[B](f: A => B): TryBagual[B] = ???
  def flatmap[B](f: A => TryBagual[B]): TryBagual[B] = ???

  def erro: Boolean = ???
  def get: A = ???
  def getOrElse[B >: A](default: B): B = if (erro) default else get
}

case class Gremio[+A](value: A) extends TryBagual[A] {
  override def map[B](f: A => B): TryBagual[B] = TryBagual(f(value))
  override def flatmap[B](f: A => TryBagual[B]): TryBagual[B] = f(value)

  override def erro: Boolean = false
  override def get: A = value
}

case class Inter[+A](t: Throwable) extends TryBagual[A] {
  override def map[B](f: A => B): TryBagual[B] = this.asInstanceOf[TryBagual[B]]
  override def flatmap[B](f: A => TryBagual[B]): TryBagual[B] = this.asInstanceOf[TryBagual[B]]

  override def erro: Boolean = true
  override def get: A = throw t
}



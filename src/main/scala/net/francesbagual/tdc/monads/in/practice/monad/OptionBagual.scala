package net.francesbagual.tdc.monads.in.practice.monad

object OptionBagual {
  def apply[A](a: A): OptionBagual[A] = if (a == null) RienDuTout else QuelqueChose(a)
}

trait OptionBagual[+A] {
  def map[B](f: A => B): OptionBagual[B] = ???
  def flatmap[B](f: A => OptionBagual[B]): OptionBagual[B] = ???

  def temDado: Boolean = ???
  def get: A = ???
  def getOrElse[B >: A](default: B): B = if (temDado) default else get
}

case class QuelqueChose[A](a: A) extends OptionBagual[A] {
  override def map[B](f: A => B): OptionBagual[B] = OptionBagual(f(a))
  override def flatmap[B](f: A => OptionBagual[B]): OptionBagual[B] = f(a)

  override def temDado: Boolean = true
  override def get: A = a
}

object RienDuTout extends OptionBagual[Nothing] {
  override def map[B](f: Nothing => B): OptionBagual[B] = RienDuTout
  override def flatmap[B](f: Nothing => OptionBagual[B]): OptionBagual[B] = RienDuTout

  override def temDado: Boolean = false
  override def get = throw new NoSuchElementException("RienDuTout.get")
}



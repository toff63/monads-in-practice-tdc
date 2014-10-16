package net.francesbagual.tdc.monads.in.practice.functor

class CanFly[A](val a:A) {
  def map[B](f: A => B):CanFly[B] = new CanFly(f(a))
}

case class Bird()
case class Duck()

object MainFunctor extends App {
  
  def toDuck(a:Bird):Duck = Duck()
  
  val supperInt:CanFly[Bird] = new CanFly[Bird](Bird())
  val supperLong:CanFly[Duck] = supperInt.map(toDuck)
}


  
  class UsingFunctor {
    
    val numbers:Seq[Int] = Seq(1,2,3,4,5)
    
    val numbersAsString:Seq[String] = numbers.map(n => n.toString())
    
  }
  

  
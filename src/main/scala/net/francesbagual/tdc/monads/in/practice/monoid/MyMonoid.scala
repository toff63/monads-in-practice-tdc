package net.francesbagual.tdc.monads.in.practice.monoid


class MyMonoid(val initialValue:Int) {
  
  def sum(seq:Seq[Int]):Int = 
    seq.fold(initialValue)((partialResult, current) => partialResult + current)
  
}



package day04

import org.apache.spark.util.AccumulatorV2

class MapAcc extends AccumulatorV2[Long ,Long ]{
  override def isZero: Boolean = ???

  override def copy(): AccumulatorV2[Nothing, Nothing] = ???

  override def reset(): Unit = ???

  override def add(v: Nothing): Unit = ???

  override def merge(other: AccumulatorV2[Nothing, Nothing]): Unit = ???

  override def value: Nothing = ???
}

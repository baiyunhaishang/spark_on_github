package day5

import scala.collection.immutable

/**
 * @Author xs
 * @Date 2020年-01月-11日 9:58:24
 **/
object test1 {
  def main(args: Array[String]): Unit = {
    val range: List[Int] = Range(1, 10).toList
    val range1: List[Int] = Range(2, 15).toList
    val range2: List[Int] = Range(10, 20).toList
    val tuples: List[(Int, Int)] = range.zip(range1)
    val tuples1: List[((Int, Int), Int)] = tuples.map((_, 1))
    val tuples2: List[((Int, Int), Int)] = tuples.map((_, 1))
    val tuples3: List[((Int, Int), Int)] = tuples1.union(tuples2)
    val map: Map[(Int, Int), Int] = tuples3.toMap
   MyPrint.printWriter(map,"aa")
  }
}
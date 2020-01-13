package day5

import org.apache.spark.{SparkConf, SparkContext}
object test3 {
  val conf: SparkConf = new SparkConf().setMaster("local[3]").setAppName("top10")
  val sc = new SparkContext(conf)
  private val list1 = List(10, 20, 30, 40, 50, 60)
  private val list2 = List("dd", 20, 30, 40, 50, 60)
  private val list3 = List("aa","ddd")
  val rdd1 = sc.parallelize(list1)
  val rdd2 = sc.parallelize(list2)
  def main(args: Array[String]): Unit = {
      one
  }
  def one={
    val flatten: List[Any] = List(list3, list1,list2).flatten
    println(flatten)
  }
}
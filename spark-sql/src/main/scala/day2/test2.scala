package day2

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Author xs
 * @Date 2020年-01月-12日 11:02:12
 **/
object test2 {
  val conf: SparkConf = new SparkConf().setMaster("local[2]").setAppName("broadcast")
  val sc = new SparkContext(conf)
  private val rdd: RDD[Int] = sc.parallelize((1 to 10), 2)
  val rdd2 = sc.parallelize(Array(("a", 10), ("a", 20), ("b", 100),
    ("c", 200), ("c", 300)))
  val rdd3 = sc.parallelize(Array(("cda", 30), ("v", 200), ("b", 100), ("c", 200), ("g", 200)))

  def main(args: Array[String]): Unit = {
    t16
  }
  def t16: Unit ={
    val list = List(1,2,3,4)

    // fold方法使用了函数柯里化，存在两个参数列表
    // 第一个参数列表为 ： 零值（初始值）
    // 第二个参数列表为：

    // fold底层其实为foldLeft
    val i = list.foldLeft(1)((x,y)=>x-y)

    val i1 = list.foldRight(10)((x,y)=>x-y)

    println(i)
    println(i1)

  }
  def t15: Unit ={
    rdd2.pipe("X:\\Users\\SwordArtOnline\\Desktop\\no_use/t15").collect.foreach(println)
  }
  def t14: Unit ={
    rdd2.union(rdd3).foreach(println)
  }
  def thirdth: Unit ={
    rdd2.subtract(rdd3).foreach(println)
  }
  def travef: Unit ={
    rdd2.intersection(rdd3).foreach(println)
  }
  def eleven: Unit ={
    rdd2.cartesian(rdd3).take(10).foreach(println)
  }
  def ten: Unit ={
    rdd3.sortByKey().foreach(print)
  }
  def night: Unit ={
    rdd2.mapValues("a"+_+"c").foreach(println)
  }
  def eight: Unit ={
    rdd2.join(rdd3).collect().foreach(println)
  }
  def seven: Unit ={
    println(rdd2.reduce((x, y) => (x._1 + y._2, x._2 + y._2)))
  }
  def six: Unit ={
    println(rdd2.countByKey())
  }
  def five: Unit ={
    println(rdd.fold(2)(_ + _))
  }
  def four: Unit ={
    val str: String = rdd.aggregate("a")(_ + _, _ + _)
    println(str)
  }
  def three: Unit ={
    val rdd: RDD[Int] = sc.makeRDD(Array(100, 30, 10, 30, 1, 50, 1, 60, 1), 2)
    rdd.foreach(println)
  }
  def one={
    val rdd1: RDD[(Int, Int)] = sc.parallelize(Array((1, 10), (2, 20), (1, 100), (3, 30)), 1)
    val rdd2 = sc.parallelize(Array((1, "a"),(2, "b"),(1, "aa"),(3, "c")),1)
    rdd1.cogroup(rdd2).collect().foreach(println)
  }
  def two: Unit ={
    val rdd1: RDD[Int] = sc.parallelize(1 to 5)
//    println(rdd1.reduce(_ + _ * 2))
//    println(rdd1.count())
//    rdd1.take(2).foreach(println)
//    println(rdd1.first())
//    rdd1.takeOrdered(2)(Ordering.Int.reverse).foreach(println)
    println(rdd1.aggregate(1)(_ + _, _ * _))
  }
}
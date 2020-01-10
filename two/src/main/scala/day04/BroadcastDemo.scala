package day04

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object BroadcastDemo {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[2]").setAppName("broadcast")
    val sc = new SparkContext(conf)
    val rdd: RDD[Int] = sc.parallelize(List(30, 50, 10, 70),4)
    val set = Set(30, 50)
    println(set)
    val bc: Broadcast[Set[Int]] = sc.broadcast(set)
    println(bc.value)
//    val rdd1: RDD[Int] = rdd.filter(bc.value.contains(_))
//    rdd1.collect().foreach(println)

  }

}

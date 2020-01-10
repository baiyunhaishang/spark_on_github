package day04

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Author xs
 * @Date 2020年-01月-10日 11:41:47
 **/
object MyACC {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[2]").setAppName("broadcast")
    val sc = new SparkContext(conf)
    val list = List(30, 50, 70, 60, 10, 20)
    val rdd: RDD[Int] = sc.parallelize(list, 4)
    val acc = new MapAcc
    sc.register(acc, "myFirstAcc")
    val rdd2: RDD[Int] = rdd.map ( x => {
      acc.add(x)
      x
    })
    rdd2.collect()
    println(acc.value)
    Thread.sleep(11111111)
    sc.stop()


  }
}
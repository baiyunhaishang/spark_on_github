package day5

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Author xs
 * @Date 2020年-01月-11日 13:13:36
 **/
object test2 {
  def main(args: Array[String]): Unit = {
    five
  }
  def five={
    val conf: SparkConf = new SparkConf().setMaster("local[3]").setAppName("top10")
    val sc = new SparkContext(conf)
    val rdd1 = sc.parallelize(List(10,20,30,40,50,60))
    val value: RDD[Array[Int]] = rdd1.glom()
  }
  def four={
    val conf: SparkConf = new SparkConf().setMaster("local[3]").setAppName("top10")
    val sc = new SparkContext(conf)
    val rdd1 = sc.parallelize(List(10,20,30,40,50,60))
    rdd1.flatMap(x=>Array(x*2)).foreach(println)
  }
  def three={
    val conf: SparkConf = new SparkConf().setMaster("local[3]").setAppName("top10")
    val sc = new SparkContext(conf)
    val rdd1 = sc.parallelize(Array(10,20,30,40,50,60))
    val res8: RDD[(Int, Int)] = rdd1.mapPartitionsWithIndex((index, items) => items.map((index, _)))
    res8.collect.foreach(println)
  }
  def two={
    for (i<- 1 to 50) {
      Thread.sleep(200)
      print("\r"+">"*(i/2)+ "="*((50-i)/2) +(i.toDouble/50*100)+"%\t进度")

    }
  }
  def one={
    val conf: SparkConf = new SparkConf().setMaster("local[2]").setAppName("top10")
    val sc = new SparkContext(conf)
    val value: RDD[Int] = sc.parallelize(Array(11, 20, 30, 43, 50, 60))
    val value1: RDD[(Int, Int)] = value.mapPartitionsWithIndex {
      (index, items) => items.map((index, _))
    }
    value1.foreach(MyPrint.printWriter(_,"cc"))
  }
}
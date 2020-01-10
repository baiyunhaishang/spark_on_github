package day1

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Author xs
 * @Date 2020年-01月-07日 21:13:39
 **/
object test6 {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("aa").setMaster("local[3]")
    val sc = new SparkContext(conf)
    val list: List[Int] = Range(1, 10).toList
    val range = Range(5, 20)
    val value: RDD[Int] = sc.parallelize(list)
    val value1: RDD[Int] = sc.parallelize(range)
    val value2: RDD[Int] = value.union(value1)
    val value3: RDD[Int] = value.intersection(value1)
    val value4: RDD[Int] = value.subtract(value1)
    val a: List[RDD[Int]] = value2 :: value3 :: value4 :: Nil
    for(i <- a){
      i.foreach(a=>print(a+" "))
      println("- ="*20)
    }

   /* val l = List(1 to 10)(0).toList
    println(l)
    val rdd: RDD[Int] = sc.parallelize(l)
    val value: RDD[Int] = rdd.filter(it =>
      it > 3 && it < 6)
    value.foreach(println)*/
  }
}

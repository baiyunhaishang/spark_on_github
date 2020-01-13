package com.bai

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.mutable


object WordCount3 {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("a").setMaster("local[2]")
    val scc = new StreamingContext(conf, Seconds(3))
    val queue: mutable.Queue[RDD[Int]] = mutable.Queue[RDD[Int]]()
    val rddStream: InputDStream[Int] = scc.queueStream(queue, false)
    rddStream.reduce(_+_).print(1000)
    scc.start()
    while (true){
      val rdd: RDD[Int] = scc.sparkContext.parallelize(1 to 100)
      queue.enqueue(rdd)
      Thread.sleep(2000)
      println(queue.length)
    }
    scc.awaitTermination()
  }
}

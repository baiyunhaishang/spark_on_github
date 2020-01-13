package com.bai.app

import com.bai.bean.{CategoryCountInfo, UserVisitAction}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Author xs
 * @Date 2020年-01月-10日 15:39:42
 **/
object MyApp {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[2]").setAppName("top10")
    val sc = new SparkContext(conf)
    val sourceRDD: RDD[String] = sc.textFile("I:\\尚硅谷资料190831\\李振超\\2019_08_30\\01_spark\\02_资料\\user_visit_action.txt")
    val userVisitActionRDD: RDD[UserVisitAction] = sourceRDD.map(line => {
      val splits: Array[String] = line.split("_")
      UserVisitAction(
        splits(0),
        splits(1).toLong,
        splits(2),
        splits(3).toLong,
        splits(4),
        splits(5),
        splits(6).toLong,
        splits(7).toLong,
        splits(8),
        splits(9),
        splits(10),
        splits(11),
        splits(12).toLong
      )
    })
//    val categoryTop10: Array[CategoryCountInfo] = CategoryTopApp.statCategoryTop10(sc, userVisitActionRDD)
//    categoryTop10.foreach(println)
//    CategoryTop10SessionApp.calCategorySessionTop10_3(sc,
//      categoryTop10,userVisitActionRDD)
    PageConversionApp.statPageConversionRate(sc, userVisitActionRDD, "1,2,3,4,5")
    sc.stop()
  }
}
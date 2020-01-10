package day1

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}
/**
 * @Author xs
 * @Date 2020年-01月-07日 19:24:15
 **/
object test5 {
  def main(args: Array[String]): Unit = {
//    var a = for (i <- Range(1,10) by 3 reverse) yield i*2
//    println(a)
    val conf: SparkConf = new SparkConf().setAppName("aa").setMaster("local[*]")
    val session: SparkSession = SparkSession.builder().config(conf).getOrCreate()
    import session.implicits._
    val rdd: RDD[(Int,String,Int)] = session.sparkContext.makeRDD(List((1,
      "lisi", 20), (3, "laowang", 33), (3, "laowang", 33)))
    val df: DataFrame = rdd.toDF("id", "name", "age")
    val ds: Dataset[User] = df.as[User]
    val df1: DataFrame = ds.toDF()
    val rdd1: RDD[Row] = df1.rdd
    rdd1.foreach(Row=>
      {println(Row.getString(1))})

    session.stop()

  }
}
case class User(id:Int,name:String,age:Int)
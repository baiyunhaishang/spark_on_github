package day2

import org.apache.spark.sql.{DataFrame, DataFrameReader, Dataset, SparkSession}

/**
 * @Author xs
 * @Date 2020年-01月-12日 11:46:09
 **/
object jdbcread {
  val spark: SparkSession = SparkSession
    .builder()
    .master("local[*]")
    .appName("aa")
    .getOrCreate()
  import spark.implicits._
  val df: DataFrame = spark.read.format("jdbc")
    .option("url", "jdbc:mysql://hadoop103:3306/azkaban")
    .option("user", "root")
    .option("password", 123456)
    .option("dbtable", "project_events ")
    .load()
  val ds: Dataset[a] = df.as[a]
  ds.createOrReplaceTempView("b")
  def main(args: Array[String]): Unit = {
    two
    spark.close()
  }
  def two: Unit ={
    spark.sql("select current_date").show()
    spark.sql("show tables").show()

  }
  def one: Unit ={


    spark.sql("select   substring(event_time,6,9) as c from b").show(10)
    spark.close()
  }
}
case class a(project_id:Long,event_type:Int,event_time:String,
             username:String,message:String)
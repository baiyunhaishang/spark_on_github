package com.baiyun

import org.apache.spark.sql.SparkSession

/**
 * @Author xs
 * @Date 2020年-01月-13日 11:17:42
 **/
object SqlApp {
  def main(args: Array[String]): Unit = {
    System.setProperty("HADOOP_USERR_NAME","atguigu")
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[*]")
      .appName("SqlApp")
      .enableHiveSupport()
      .getOrCreate()
    import spark.implicits._
    spark.sql(" use sql0830")
    spark.sql(
      """
        |select
        |ci.*,
        |pi.product_name,
        |uv.click_product_id
        |from user_visit_action uv
        |join product_info pi on uv.click_product_id = pi.product_id
        |join city_info ci on uv.city_id =ci.city_id
      """.stripMargin).createOrReplaceTempView("t1")
    spark.udf.register("remark",RemarkUDAF)
    spark.sql(
      """
        |select
        |area,
        |product_name,
        |count(*) ct,
        |remark(city_name) remark
        |from t1
        |group by area ,product_name
      """.stripMargin).createOrReplaceTempView("t2")
    spark.sql(
      """
        | select
        | *,
        |rank() over(partition by area order by ct desc)rk
        |from t2
      """.stripMargin).createOrReplaceTempView("t3")

    spark.sql(
      """
        |select
        |area,
        |product_name,
        |ct,
        |remark
        |from t3
        |where rk<=3
      """.stripMargin).show(50,false)
    spark.close()
  }
}
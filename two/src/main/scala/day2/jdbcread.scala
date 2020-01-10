package day2

import java.sql.{DriverManager, ResultSet}

import org.apache.spark.rdd.{JdbcRDD, RDD}
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Author xs
 * @Date 2020年-01月-08日 19:16:17
 **/
object jdbcread {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("jdbcread").setMaster("local")
    val sc = new SparkContext(conf)
    val driver = "com.mysql.jdbc.Driver"
    val url = "jdbc:mysql://localhost:3306/test"
    val username = "root"
    val password = "123456"
    val sql = "select * from user where id >=?  and id <=?"
    val rdd:RDD[(Int,String)] = new JdbcRDD(
      sc,
      () => {
        Class.forName(driver)
        DriverManager.getConnection(url, username, password)
      },
      sql,
      1,
      100,
      2,
      (resultSet: ResultSet) => {
        (resultSet.getInt(1), resultSet.getString(2))
      }
    )
    rdd.collect().foreach(println)
  }
}
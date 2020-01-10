package day2

import java.sql.{Connection, DriverManager, PreparedStatement}

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Author xs
 * @Date 2020年-01月-08日 18:44:12
 **/
object test21 {
  def main(args: Array[String]): Unit = {
    val list = List((1, "aa"), (4, "add"), (44, "adsa"), (453, "adsaa"))
    val conf: SparkConf = new SparkConf().setAppName("a").setMaster("local")
    val sc = new SparkContext(conf)
    val driver = "com.mysql.jdbc.Driver"
    val url = "jdbc:mysql://localhost:3306/test"
    val username = "root"
    val password = "123456"
    val sql = "insert into user values(?,?)"
    val rdd: RDD[(Int, String)] = sc.parallelize(list, 2)
    rdd.foreachPartition(it=>{
      Class.forName(driver)
      val con: Connection = DriverManager.getConnection(url, username, password)
      val ps: PreparedStatement = con.prepareStatement(sql)
      it.foreach({
        case (id,name)=>
          ps.setInt(1,id)
          ps.setString(2,name)
          ps.addBatch()
      })
      ps.executeBatch()
      con.close()
    })
    sc.stop()
  }
}
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * @Author xs
 * @Date 2020年-01月-12日 8:55:17
 **/
object test {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[*]")
      .appName("aa")
      .getOrCreate()
      spark.udf.register("aa",(str:String)=>{str.toUpperCase()})
     spark.sql("select aa('jds')").show()
    val frame: DataFrame = spark.read.json("spark-sql/input/a.json")
    frame.createOrReplaceTempView("aa")
    spark.sql("select aa(cc),a,b from aa").show(2)
  }
}
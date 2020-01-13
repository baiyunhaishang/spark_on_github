import org.apache.spark.sql.{Dataset, SparkSession}

/**
 * @Author xs
 * @Date 2020年-01月-11日 18:54:38
 **/
object CreateDS {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[*]")
      .appName("aa")
      .getOrCreate()
    import spark.implicits._
    val seq = Seq(Person("lisi", 20), Person("zs", 21))
    val ds: Dataset[Person] = seq.toDS()
    ds.createOrReplaceTempView("p")
    spark.sql("select * from p where age=21").show(10)
    spark.close()
  }
}

case class Person(name: String, age: Int)
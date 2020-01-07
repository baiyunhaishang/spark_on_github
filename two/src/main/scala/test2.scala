import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @ClassName test2
 * @Author xs
 * @Date 2020年-01月-07日 10:35:31
 * @Version V1.0
 **/
object test2 {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("aa").setMaster("local[3]")
    val sc = new SparkContext(conf)
    val rdd: RDD[String] = sc.textFile("I:\\尚硅谷资料190831\\李振超\\2019_08_30\\01_spark\\02_资料/agent.log")
    println(rdd.take(3))

  }

}
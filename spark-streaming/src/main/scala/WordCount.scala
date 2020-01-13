import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * @Author xs
 * @Date 2020年-01月-13日 15:31:17
 **/
object WordCount {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[2]").setAppName("aa")
    val ssc = new StreamingContext(conf, Seconds(3))
    val sourceStream: ReceiverInputDStream[String] = ssc.socketTextStream("hadoop103", 9999)
    val wordAndCount: DStream[(String, Int)] = sourceStream.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)
    wordAndCount.print(1000)
    ssc.start()
    ssc.awaitTermination()
  }
}
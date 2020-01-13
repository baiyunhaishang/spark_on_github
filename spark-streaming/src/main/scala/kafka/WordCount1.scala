package kafka
import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * @Author xs
 * @Date 2020年-01月-13日 18:10:47
 **/
object WordCount1 {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("aa").setMaster("local[2]")
    val ssc = new StreamingContext(conf, Seconds(3))
    val params: Map[String, String] =Map[String,String](
      "group.id"->"0830",
      "bootstrap.servers"->"hadoop102:9092,hadoop103:9092,hadoop104:9092"
    )
   /* val sourceStream: InputDStream[(String, String)] = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
      ssc, parms, Set("s0830"))
    sourceStream
      .map{
      case (k,v) => v
      }
      .flatMap(_.split("\\W+"))
      .map((_,1))
      .reduceByKey(_+_)
      .print(1000)*/
   val sourceStream: InputDStream[(String, String)] = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
     ssc,
     params,
     Set("s0830"))
    sourceStream
      .map {
        case (_, v) => v
      }
      .flatMap(_.split("\\W+"))
      .map((_, 1))
      .reduceByKey(_ + _)
      .print(2)
    ssc.start()
    ssc.awaitTermination()
  }
}
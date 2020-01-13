package com.bai

import java.io.{BufferedReader, InputStreamReader}
import java.net.Socket

import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.dstream.ReceiverInputDStream
import org.apache.spark.streaming.receiver.Receiver
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * @Author xs
 * @Date 2020年-01月-13日 19:01:05
 **/
object MyReceiverDemo {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("dd").setMaster("local[2]")
    val ssc = new StreamingContext(conf, Seconds(3))
    val lineStream: ReceiverInputDStream[String] = ssc.receiverStream(new MyReceiver("hadoop103", 10000))
    lineStream.flatMap(_.split("\\W+")).map((_, 1)).reduceByKey(_ + _).print(100)
    ssc.start()
    ssc.awaitTermination()
  }
}

case class MyReceiver(host: String, port: Int) extends Receiver[String](StorageLevel.MEMORY_ONLY) {


  var socket: Socket = _
  var reader: BufferedReader = _

  override def onStart(): Unit = {
    runInThread {
      try {
  socket=new Socket(host,port)
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream,"utf-8"))
        var line: String = reader.readLine()
        while (socket.isConnected && line !=null){
          store(line)
          line =reader.readLine()
        }
      }catch {
        case e => println(e.getMessage)
      }finally {
        restart("restarting...")
      }
    }
  }

  override def onStop(): Unit = {
    if(socket!=null){
      socket.close()
    }
    if (reader !=null){
      reader.close()
    }
  }

  def runInThread(f: => Unit): Unit = {
    new Thread() {
      override def run() = f
    }.start()
  }
}
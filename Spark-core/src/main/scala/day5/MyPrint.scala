package day5

import java.io.{File, PrintWriter}

/**
 * @Author xs
 * @Date 2020年-01月-11日 8:30:31
 **/
object MyPrint {

    //文件写入
  def printWriter(i:Any,path:String="text.txt")= {
    val writer = new PrintWriter(new File(s"Spark-core/out/${path}"))
    writer.print(i + " ")
    writer.close()

  }

  def main(args: Array[String]): Unit = {

  }

  printWriter((1,2),"b")
}

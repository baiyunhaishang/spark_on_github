/**
 * @Author xs
 * @Date 2020年-01月-11日 8:44:35
 **/

import  java.io.PrintWriter
import  java.io.File
import  scala.reflect.io.Directory

object  readAllDir{
  def  main(args:Array[String]):Unit  =  {
    for(d  <-  subDir(new  File("X:\\Users\\SwordArtOnline\\Desktop\\no_use")))
      println(d)
  }

  def  subDir(dir:File):Iterator[File]  ={
    val  children  =  dir.listFiles().filter(_.isDirectory())
    children.toIterator  ++  children.toIterator.flatMap(subDir  _)
  }
}
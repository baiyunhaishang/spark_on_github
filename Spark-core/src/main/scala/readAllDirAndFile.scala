import java.io.File

/**
 * @Author xs
 * @Date 2020年-01月-11日 8:40:23
 **/
object readAllDirAndFile{

  def main(args:Array[String]):Unit  =  {
    for(d <-  subDir(new  File("X:\\Users\\SwordArtOnline\\Desktop\\no_use")))
      println(d)
  }

  def  subDir(dir:File):Iterator[File]  ={
    val  dirs  =  dir.listFiles().filter(_.isDirectory())
    val  files  =  dir.listFiles().filter(_.isFile())
    files.toIterator  ++  dirs.toIterator.flatMap(subDir  _)
  }

}

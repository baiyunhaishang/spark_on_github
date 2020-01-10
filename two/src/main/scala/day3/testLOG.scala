package day3

/**
 * @Author xs
 * @Date 2020年-01月-09日 14:57:45
 **/
object testLOG {
  def main(args: Array[String]): Unit = {
    /*println(math.log(8))
    println(math.log(2))
    println(math.log(math.E))
    println(math.E)
    println(math.log10(1000))*/
    var a = 9646324351L
    println(reverse(-123))
  }

    def reverse(x: Int): Int = {
      if (x >= 0 && x < Int.MaxValue) x.toString.reverse.toInt else -((-x).toString.reverse.toInt)
    }

}
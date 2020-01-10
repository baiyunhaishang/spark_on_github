package day3

object BinaryTree {
  def main(args: Array[String]): Unit = {
    val tree: BinaryTree[Int] = initOneBinaryTree()
    tree.preForech(println)
  }
  def initOneBinaryTree() = {
    val n10 = new TreeNode[Int](10)
    val n9 = new TreeNode[Int](9)
    val n20= new TreeNode[Int](20)
    val n15 = new TreeNode[Int](15)
    val n35 = new TreeNode[Int](35)
    n10.left =n9
    n10.right =n20
    n9.p = n10
    n20.p = n10
    n20.p = n10
    n20.left = n15
    n20.right = n35
    n15.p = n20
    n35.p = n20
    val tree = new BinaryTree[Int]
    tree.root = n10
    tree
  }

}
class BinaryTree[T]{
  def preForech(op : T=>Unit): Unit = {
    if ( root != null){
      root.preForech(op)
    }
  }

  var root:TreeNode[T] = _
}
class TreeNode[T](val value:T){
  var left:TreeNode[T] = _
  var right:TreeNode[T] = _
  var p:TreeNode[T] = _
  def preForech(op: T =>Unit):Unit = {
    op(value)
    if (left != null) left.preForech(op)
    if (right != null) right.preForech(op)
  }
}
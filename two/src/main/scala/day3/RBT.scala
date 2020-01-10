package day3

import javax.swing.tree.TreeNode

/**
 * @Author xs
 * @Date 2020年-01月-09日 11:35:37
 **/
object RBT {
  def main(args: Array[String]): Unit = {

  }
}
/*

  def invertTree(root: TreeNode): TreeNode = {
    if (root != null) {
      var t: TreeNode = root.left
      root.left = root.right
      invertTree(root.left)
      invertTree(root.right)
      root
    } else {
      null
    }
  }

}

case class TreeNode(var val1: Int, var left: TreeNode = null, var right: TreeNode = null)
*/

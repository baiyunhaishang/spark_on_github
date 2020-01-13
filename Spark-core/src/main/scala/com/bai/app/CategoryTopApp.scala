package com.bai.app

import com.bai.CategoryAcc
import com.bai.bean.{CategoryCountInfo, UserVisitAction}
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

/**
 * @Author xs
 * @Date 2020年-01月-10日 15:53:41
 **/
object CategoryTopApp {
  def statCategoryTop10(sc: SparkContext, userVisitActionRDD: RDD[UserVisitAction]):Array[CategoryCountInfo]= {
    val acc = new CategoryAcc
    val value: Any = sc.register(acc, "Cate")
    userVisitActionRDD.foreach(action => {
      acc.add(action)
    })
    val cidActionAndCountGrouped: Map[String, Map[(String, String), Long]] = acc.value.groupBy(_._1._1)
    val catActionAndCountInfos: Array[CategoryCountInfo] = cidActionAndCountGrouped.map {
      case (cid, map) =>
        CategoryCountInfo(cid,
          map.getOrElse((cid, "click"), 0),
          map.getOrElse((cid, "order"), 0),
          map.getOrElse((cid, "pay"), 0)
        )
    }.toArray
    catActionAndCountInfos.sortBy(info =>(-info.clickCount,
      -info.orderCount,-info.payCount)).take(10)
  }
}
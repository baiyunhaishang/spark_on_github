package com.bai.app

import java.text.DecimalFormat

import com.bai.bean.UserVisitAction
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

object PageConversionApp {
  def statPageConversionRate(sc:SparkContext,
                             userVisitActionRDD:RDD[UserVisitAction],
                             pages:String)={
    println("kk")
    val splits: Array[String] = pages.split(",")
    val prePages: Array[String] = splits.slice(0, splits.length - 1)
    val postPages: Array[String] = splits.slice(1, splits.length)
    val pageFlow: Array[String] =prePages.zip(postPages).map{
      case (prePage,postPage)=>s"$prePages-$postPages"
    }
    val pageAndCount: collection.Map[Long, Long] = userVisitActionRDD
      .filter(action => prePages.contains(action.page_id.toString))
      .map(action => (action.page_id, 1))
      .countByKey()
    val sessionIdGrouped: RDD[(String, Iterable[UserVisitAction])] = userVisitActionRDD.groupBy(_.session_id)
    val totalPageFlows: collection.Map[String, Long] = sessionIdGrouped.flatMap({
      case (sessionId, actionIt) =>
        val actions: List[UserVisitAction] = actionIt.toList.sortBy(_.action_time)
        val preActions: List[UserVisitAction] = actions.slice(0, actions.length - 1)
        val postActions: List[UserVisitAction] = actions.slice(1, actions.length)
        postActions.zip(postActions).map({
          case (preActions, postActions) =>
            s"${preActions.page_id}->${postActions.page_id}"
        }).filter(flow => pageFlow.contains(flow))
    }).map((_, 1)).countByKey()
    val result: collection.Map[String, String] = totalPageFlows.map({
      case (pageFlow, count) =>
        val formatter = new DecimalFormat(".00%")
        val rate: Double = count.toDouble / pageAndCount(pageFlow.split("->")(0).toLong)
        println("jj")
        println(pageFlow)
        (pageFlow, formatter.format(rate))
    })
    result.foreach(println)
  }

  def main(args: Array[String]): Unit = {

  }
}

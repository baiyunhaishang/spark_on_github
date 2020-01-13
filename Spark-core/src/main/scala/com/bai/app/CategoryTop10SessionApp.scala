package com.bai.app

import com.bai.bean.{CategoryCountInfo, SessionInfo, UserVisitAction}
import day5.MyPrint
import org.apache.spark.{Partitioner, SparkContext}
import org.apache.spark.rdd.RDD

import scala.collection.mutable

/**
 * @Author xs
 * @Date 2020年-01月-11日 10:19:10
 **/
object CategoryTop10SessionApp {
  def calCategorySessionTop10(sc: SparkContext,
                              categoryTop10: Array[CategoryCountInfo],
                              userVisitActionRDD: RDD[UserVisitAction]) = {
    val cids: Array[Long] = categoryTop10.map(_.categoryId.toLong)
    val filteredUserVisitActionRDD: RDD[UserVisitAction] =
      userVisitActionRDD.filter(action => cids.contains(action.click_category_id))
    val cidSessionAndOne: RDD[((Long, String), Int)] = filteredUserVisitActionRDD.map(action => ((action.click_category_id,
      action.session_id), 1))
    val cidAndSessionCount: RDD[(Long, (String, Int))] = cidSessionAndOne.reduceByKey(_ + _).map {
      case ((cid, sid), count) => (cid, (sid, count))
    }
    val cidAndSessionCountGrouped: RDD[(Long, Iterable[(String, Int)])] = cidAndSessionCount.groupByKey()
    val resultRDD: RDD[(Long, List[(String, Int)])] = cidAndSessionCountGrouped.mapValues(it => {
      it.toList.sortBy(-_._2).take(10)
    })
    resultRDD.collect.foreach(println)
  }

  def calCategorySessionTop10_1(sc: SparkContext,
                                categoryTop10: Array[CategoryCountInfo],
                                userVisitActionRDD: RDD[UserVisitAction]) = {
    val cids: Array[Long] = categoryTop10.map(_.categoryId.toLong)
    val filteredUserVisitActionRDD: RDD[UserVisitAction] =
      userVisitActionRDD.filter(action => cids.contains(action.click_category_id))
    for (cid <- cids) {
      val cidRDD: RDD[UserVisitAction] =
        filteredUserVisitActionRDD.filter(_.click_category_id == cid)
      val cidSidAndCount: RDD[((Long, String), Int)] = cidRDD
        .map(action => ((action.click_category_id, action.session_id), 1))
        .reduceByKey(_ + _)
      val result: Map[Long, List[(String, Int)]] = cidSidAndCount.sortBy(-_._2)
        .map {
          case ((cid, sid), cout) => (cid, (sid, cout))
        }
        .take(10)
        .groupBy(_._1)
        .map({
          case (cid, arr) => (cid, arr.map(_._2).toList)
        })
      println(result.toList)
    }


  }

  def calCategorySessionTop10_2(sc: SparkContext,
                                categoryTop10: Array[CategoryCountInfo],
                                userVisitActionRDD: RDD[UserVisitAction]) = {
    val cids: Array[Long] = categoryTop10.map(_.categoryId.toLong)
    val filteredUserVisitActionRDD: RDD[UserVisitAction] =
      userVisitActionRDD.filter(action => cids.contains(action.click_category_id))
    val cidSessionAndOne: RDD[((Long, String), Int)] = filteredUserVisitActionRDD.map(action => ((action.click_category_id, action.session_id), 1))
    val cidAndSessionCount: RDD[(Long, (String, Int))] = cidSessionAndOne.reduceByKey(_ + _).map {
      case ((cid, sid), count) => (cid, (sid, count))
    }
    val cidAndSessionCountGroup: RDD[(Long, Iterable[(String, Int)])] = cidAndSessionCount.groupByKey()
    val resultRDD: RDD[(Long, List[SessionInfo])] = cidAndSessionCountGroup.mapValues(f = it => {
      var set = new mutable.TreeSet[SessionInfo]()
      it.foreach {
        case (sessionId, count) =>
          set += SessionInfo(sessionId, count)
          if (set.size > 10) set = set.take(10)
      }
      set.toList
    })
    resultRDD.collect.foreach(x=>MyPrint.printWriter(x+"\n","dd"))

  }

  def calCategorySessionTop10_3(sc: SparkContext,
                                categoryTop10: Array[CategoryCountInfo],
                                userVisitActionRDD: RDD[UserVisitAction]) = {
    val cids: Array[Long] = categoryTop10.map(_.categoryId.toLong)
    val filteredUserVisitActionRDD: RDD[UserVisitAction] =
      userVisitActionRDD.filter(action => cids.contains(action.click_category_id))
    val cidSessionAndOne: RDD[((Long, String), Int)] = filteredUserVisitActionRDD.map(action => ((action.click_category_id, action.session_id), 1))
    val cidAndSessionCount: RDD[(Long, (String, Int))] = cidSessionAndOne.reduceByKey(_ + _).map {
      case ((cid, sid), count) => (cid, (sid, count))
    }
    val resultRDD: RDD[(Long, SessionInfo)] = cidAndSessionCount.mapPartitions(it => {
      var set = new mutable.TreeSet[SessionInfo]()
      var categoryId = 0L
      it.foreach {
        case (cid, (sessinonId, count)) =>
          categoryId = cid
          set += SessionInfo(sessinonId, count)
          if (set.size > 10) set = set.take(10)
      }
      set.map((categoryId, _)).toIterator
    })
    resultRDD.collect.foreach(println)

  }
}
class CategoryPartition(cids:Array[Long])extends Partitioner{
  private val map: Map[Long, Int] = cids.zipWithIndex.toMap
  override def numPartitions: Int = cids.length

  override def getPartition(key: Any): Int = {
    key match {
      case (k:Long,_)=>map(k)
    }
  }
}


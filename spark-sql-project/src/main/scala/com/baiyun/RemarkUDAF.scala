package com.baiyun

import java.text.DecimalFormat

import org.apache.spark.sql.{Row, types}
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types._

object RemarkUDAF extends UserDefinedAggregateFunction{
  override def inputSchema: StructType = StructType(StructField("city_name",StringType)::Nil)

  override def bufferSchema: StructType =
    StructType(StructField("map",MapType(StringType,LongType))::StructField("total",LongType)::Nil)

  override def dataType: DataType = StringType

  override def deterministic: Boolean = true

  override def initialize(buffer: MutableAggregationBuffer): Unit = {
    buffer(0)=Map[String,Long]()
    buffer(1)=0L
  }

  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
    if(!input.isNullAt(0)){
      val cityName: String = input.getString(0)
      buffer(1)=buffer.getLong(1)+1L
      val map: collection.Map[String, Long] = buffer.getMap[String, Long](0)
      buffer(0)=map+(cityName->(map.getOrElse(cityName,0L)+1))
    }
  }

  override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
   buffer1(1)=buffer1.getLong(1)+buffer2.getLong(1)
    val map1: collection.Map[String, Long] = buffer1.getMap[String, Long](0)
    val map2: collection.Map[String, Long] = buffer2.getMap[String, Long](0)
    buffer1(0)=map1.foldLeft(map2){
      case(map,(cityName,count))=>
        map + (cityName->(map.getOrElse(cityName,0L)+count))
    }
  }

  override def evaluate(buffer: Row): Any = {
    val cityAndCount: collection.Map[String, Long] = buffer.getMap[String, Long](0)
    val total: Long = buffer.getLong(1)
    val cityAndCountTop2: List[(String, Long)] = cityAndCount.toList.sortBy(-_._2).take(2)
    val cityRemarkTop2: List[CityRemark] = cityAndCountTop2.map {
      case (cityName, count) =>
        CityRemark(cityName, count.toDouble / total)
    }
    val result=cityRemarkTop2 :+CityRemark("其他",(1D/:cityRemarkTop2)(_-_.cityRatio))
    result.mkString(",")
  }
}

case class CityRemark(cityName: String, cityRatio: Double){
  private val f = new DecimalFormat(".00%")
  override def toString: String =s"$cityName:${f.format(cityRatio)}"
}

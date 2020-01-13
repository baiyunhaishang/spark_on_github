package day2

import org.apache.spark.sql.Row
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types.{DataType, DoubleType, StructField, StructType}

/**
 * @Author xs
 * @Date 2020年-01月-12日 9:59:37
 **/
object udf {
  def main(args: Array[String]): Unit = {

  }
}
class MySum extends UserDefinedAggregateFunction{
  override def inputSchema: StructType = StructType(StructField("c",dataType)::Nil)

  override def bufferSchema: StructType = StructType(StructField("sum",DoubleType)::Nil)

  override def dataType: DataType =DoubleType

  override def deterministic: Boolean = true

  override def initialize(buffer: MutableAggregationBuffer): Unit = buffer(0)=0D

  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
    if (!input.isNullAt(0)){
      val value: Double = input.getDouble(0)
      buffer(0)=buffer.getDouble(0)+value
    }
  }

  override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = ???

  override def evaluate(buffer: Row): Any = ???
}
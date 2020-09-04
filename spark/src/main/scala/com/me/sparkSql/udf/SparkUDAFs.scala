package com.me.sparkSql.udf

import org.apache.spark.sql.{Row, SQLContext}
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types.{ArrayType, DataType, LongType, StringType, StructField, StructType}
import scala.collection.mutable.ListBuffer
import util.control.Breaks._

object SparkUDAFs {
  def register(sqlContext: SQLContext): Unit = {
    sqlContext.udf.register("missingSeq", NfvUdf)
  }

  private def myCount(arr: Seq[String]) = {
    arr.length
  }
}

object NfvUdf extends UserDefinedAggregateFunction {

  //输入数据的类型
  override def inputSchema: StructType = StructType(List(
    StructField("alarmseq", StringType)
  ))

  //产生中间结果的数据类型
  override def bufferSchema: StructType = StructType(List(
    StructField("sequences", ArrayType(LongType, false))
  ))

  // 输出的结果类型
  override def dataType: DataType = ArrayType(StringType)

  //确保一致性，一般用true
  override def deterministic: Boolean = true

  //指定初始值
  override def initialize(buffer: MutableAggregationBuffer): Unit = {
    buffer(0) = Seq[Long]()
  }

  //update
  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
    //每有一个数字参与运算就进行相乘(包含中间结果)
    buffer(0) = buffer.getAs[Seq[Long]](0) :+ input.getAs[String](0).toLong
  }

  //全局聚合
  override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
    //每个分区缓存合并
    buffer1(0) = buffer1.getAs[Seq[Long]](0) ++ buffer2.getAs[Seq[Long]](0)
  }

  //计算最终的结果
  override def evaluate(buffer: Row): Any = {
    val seqList = buffer.getAs[Seq[Long]](0)
    val sortedSeq = seqList.sortWith((a, b) => a <= b)
    val missing = new ListBuffer[String]()
    // 如果存在缺失
    val missedSize = sortedSeq.last - sortedSeq.head + 1 - sortedSeq.size
    if (missedSize > 0) {
      var processedSize = 0L
      breakable {
        for (pre <- sortedSeq.indices) {
          val next = pre + 1
          if (next < sortedSeq.size) {
            val misses = sortedSeq(next) - sortedSeq(pre) - 1
            if (misses == 1) {
              missing.append((sortedSeq(pre) + 1).toString)
            } else if (misses > 1) {
              val start = sortedSeq(pre) + 1
              val end = sortedSeq(next) - 1
              missing.append(s"${start}~${end}")
            }
            processedSize += misses
          }
          if (processedSize >= missedSize) {
            break()
          }
        }
      }
    }
    missing
  }
}
package com.me.structedStreaming
import org.apache.spark.sql.functions._
import org.apache.spark.sql.SparkSession

object StructedStreamingTest {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .appName("StructuredNetworkWordCount")
      .getOrCreate()
    import spark.implicits._
  }
}

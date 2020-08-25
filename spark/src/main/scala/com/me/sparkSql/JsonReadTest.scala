package com.me.sparkSql

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{StructField, StructType, _}

object JsonReadTest {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("test").setMaster("local[2]")
    conf.set("spark.sql.crossJoin.enabled", "true")
    conf.set("spark.sql.caseSensitive", "true")
    val sparkSession = SparkSession.builder().config(conf).getOrCreate()
    sparkSession.sparkContext.setLogLevel("WARN")
    val sqlContext = sparkSession.sqlContext
    val schemaFields = List(StructField("startTime", StringType), StructField("endTime", StringType))
    val structType = StructType(schemaFields)
    val df = sqlContext.read.schema(structType).json("file:///C:\\Users\\gaodaibin\\Desktop\\NFV\\json\\timestamp.json")
    df.createOrReplaceTempView("test")
    val resDF = sparkSession.sqlContext.sql("select * from test where to_unix_timestamp(endTime, 'yyyy-MM-dd HH:mm:ss') - to_unix_timestamp(startTime, 'yyyy-MM-dd HH:mm:ss') >35")
    resDF.show(10)
    df.printSchema()
    df.show(10)
    sparkSession.stop()
  }
}

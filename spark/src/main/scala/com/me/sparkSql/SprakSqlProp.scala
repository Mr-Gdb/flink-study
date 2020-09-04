package com.me.sparkSql

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object SprakSqlProp {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("test").setMaster("local[2]")
    val sparkSession = SparkSession.builder().config(conf).getOrCreate()
    sparkSession.sql("SET -v").show(numRows = 100, truncate = false)
    sparkSession.close()
  }
}

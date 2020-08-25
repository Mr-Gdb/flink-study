package com.me.sparkCore

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
 * 一、什么是RDD
 * 二、怎么生成RDD:
 *    1、通过scala集合创建，多用于测试，接口：sc.parallelize
 *    2、通过外部文件系统创建，接口：sc.testFile
 * 三、RDD的使用（transform和action）
 * 四、缓存
 * 五、广播变量和累加器
 */

object RddTest {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("test").setMaster("local[3]")
    val spark = SparkSession.builder.config(conf).getOrCreate()
    spark.sparkContext.setLogLevel("WARN")
    val names = List("zhangpp","gaodb")
    val nameRdd = spark.sparkContext.parallelize(names)
    val loveRdd = nameRdd.map(_ + "love")
    loveRdd.foreach(println(_))
    val line: RDD[String] = spark.sparkContext.textFile("file:///C:\\Users\\gaodaibin\\Desktop\\NFV\\json\\test.txt")
    println(line.partitions.size)
    spark.close()
  }
}

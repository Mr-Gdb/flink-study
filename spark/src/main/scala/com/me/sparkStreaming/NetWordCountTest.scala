package com.me.sparkStreaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * sparkStreaming 的编程模型
 *
 * 1、创建StreamingContext
 * 2、根据StreamingContext创建DStream
 * 3、对DStream做转换操作
 * 4、对计算结果进行输出
 * 5、提交spark streaming程序，等待结束
 */
object NetWordCountTest {
  def main(args: Array[String]): Unit = {

    // 1、创建StreamingContext
    val conf = new SparkConf().setMaster("local[2]").setAppName("net word count")
    val ssc = new StreamingContext(conf, Seconds(5))

    // 2、构建DStream

    // 3、对DStream做操作

    // 4、结果输出

    // 5、提交spark stream程序
    ssc.start()
    ssc.awaitTermination()
  }
}

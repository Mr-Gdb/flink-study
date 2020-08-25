package com.me.sparkStreaming

import java.nio.charset.StandardCharsets
import java.util

import org.apache.rocketmq.spark.{ConsumerStrategy, LocationStrategy, RocketMqUtils}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object MyRocketMqUtils extends {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[2]").setAppName("mqtest")
    //conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
    val ssc = new StreamingContext(conf, Seconds(10))
    val topicSet = new util.HashSet[String]()
    val params = new util.HashMap[String, String]()
    topicSet.add("epic-north-performance-topic")
    //val params: Map[String, String] = Map("nameserver.addr" -> "10.154.5.98:9876")
    params.put("nameserver.addr", "10.154.5.96:9876;10.154.5.98:9876")
    val ds = RocketMqUtils.createMQPullStream(ssc, "test",
              topicSet, ConsumerStrategy.lastest,
      true, false, false, LocationStrategy.PreferConsistent, params)
    ds.foreachRDD((rdd, time) => {
      rdd.foreach(x => println(new String(x.getBody, StandardCharsets.UTF_8)))
    })
    ssc.start()
    ssc.awaitTermination()
  }
}

package com.me.sparkSql

import com.me.sparkSql.udf.SparkUDAFs
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object NfvKafkaTest {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("test").setMaster("local[2]")
    conf.set("spark.sql.crossJoin.enabled", "true")
    conf.set("spark.sql.caseSensitive", "false")
    conf.set("spark.sql.columnNameOfCorruptRecord", "error")
    val sparkSession = SparkSession.builder().config(conf).getOrCreate()
    sparkSession.sparkContext.setLogLevel("WARN")
    val sqlContext = sparkSession.sqlContext
    SparkUDAFs.register(sqlContext)
    val df = sqlContext.read.json("file:///C:\\Users\\gaodaibin\\Desktop\\NFV\\data\\file5.json")
    df.printSchema()
    df.show(3)
    df.createOrReplaceTempView("test")
    //val sql = "select nfvoid,substr(eventtime,0,10) as eventdate, max(bigint(alarmseq)) as max, min(bigint(alarmseq)) as min,count(1) as total_num,(max(bigint(alarmseq))- min(bigint(alarmseq)) + 1) - count(1) as prob_num from test group by nfvoid,eventdate"
    val sql = "select nfvoid,max(bigint(alarmseq)) as max,min(bigint(alarmseq)) as min from test  group by nfvoid "
    val detailSql = "select nfvoid,substr(eventtime,0,10) as eventdate,missingSeq(alarmseq) as missed,max(bigint(alarmseq)) as max,min(bigint(alarmseq)) as min,max(bigint(alarmseq)) - min(bigint(alarmseq)) + 1 as total_num,(max(bigint(alarmseq))- min(bigint(alarmseq)) + 1) - count(distinct alarmseq) as prob_num from test  where submessagetype != '11005' group by nfvoid,eventdate"
    //val sql = "select error from test"
    //val sql = "select * from (select nfvoid,substr(eventtime,0,10) as eventdate, max(bigint(alarmseq)) as max, min(bigint(alarmseq)) as min,count(1) as total_num,(max(bigint(alarmseq))- min(bigint(alarmseq)) + 1) - count(1) as prob_num from test group by nfvoid,eventdate) tmp where tmp.prob_num > 0"
    val resDF = sparkSession.sqlContext.sql(detailSql)
    resDF.show(20, false)
    sparkSession.stop()

  }

}

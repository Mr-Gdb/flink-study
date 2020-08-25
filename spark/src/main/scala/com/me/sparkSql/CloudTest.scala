package com.me.sparkSql

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{StringType, StructField, StructType}

object CloudTest {
  def main(args: Array[String]): Unit = {
    val fields = List("resourceid", "compositetarget", "target", "value", "timestamp")

    val conf = new SparkConf().setAppName("test").setMaster("local[2]")
    conf.set("spark.sql.crossJoin.enabled", "true")
    conf.set("spark.sql.caseSensitive", "false")
    val sparkSession = SparkSession.builder().config(conf).getOrCreate()
    sparkSession.sparkContext.setLogLevel("WARN")
    val sqlContext = sparkSession.sqlContext
    val schemaFields = fields.map(x => StructField(x.toLowerCase, StringType))
    val structType = StructType(schemaFields)
    val df = sqlContext.read.schema(structType).json("file:///C:\\Users\\gaodaibin\\Desktop\\NFV\\json\\all.json")
    println(df.count())
    df.createOrReplaceTempView("cm_cloud")
    val sql = "select starttime,sum(1) as total_num,sum(case when t_c2 is null then 1 else 0 end) as prob_num from (select t1.compositetarget as t_c1,t2.compositetarget as t_c2,starttime from (select distinct compositetarget,CAST((timestamp - timestamp % 300) AS BIGINT) as starttime from cm_cloud inner join (select min(CAST((timestamp - timestamp % 300) AS BIGINT)) as min_timestamp from cm_cloud)tmp1 on CAST((timestamp - timestamp % 300) AS BIGINT)=min_timestamp+300)t1 left join (select distinct compositetarget from cm_cloud inner join (select min(CAST((timestamp - timestamp % 300) AS BIGINT)) as min_timestamp from cm_cloud)tmp1 on CAST((timestamp - timestamp % 300) AS BIGINT)=min_timestamp+600)t2 on t1.compositetarget = t2.compositetarget)tmp group by starttime"
    //val sql1 = "select starttime, eventtime,to_unix_timestamp(starttime, 'yyyy-MM-dd HH:mm:ss') as stime, to_unix_timestamp(eventtime, 'yyyy-mm-dd hh:mm:ss') as etime from test"
    val resDF = sparkSession.sqlContext.sql(sql)
    resDF.show(3)
    sparkSession.stop()
  }
}

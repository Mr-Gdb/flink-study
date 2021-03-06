package com.me.sparkSql

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{StringType, StructField, StructType}

object NfvTest {
  def main(args: Array[String]): Unit = {
    val fields = List("alarmStatus", "specialty1", "specialty2", "specialty3", "DelayTime", "dataSource",
                      "sourceID", "endTime", "relationRuleName", "loadTime", "objectName", "vmIdList", "relationType",
                      "nfvoId", "subMessageType", "realationRuleName", "correlatedAlarmIdList", "eventTime", "startTime",
                      "correlatedTime", "alarmSeq", "hostIdList", "subObjectType", "messageType", "addInfo", "alarmTitle",
                      "specificProblemID", "specificProblem", "alarmType", "vendorName", "objectType", "linkId", "ClearTime",
                      "RelationFlag", "RelationTime", "createTime", "ClearRuleId", "pflag", "province", "relationRuleId",
                      "ClearUser", "objectUID", "subObjectName", "origSeverity", "ClearTimeStamp", "subObjectUID")

    val conf = new SparkConf().setAppName("test").setMaster("local[2]")
    conf.set("spark.sql.crossJoin.enabled", "true")
    conf.set("spark.sql.caseSensitive", "false")
    val sparkSession = SparkSession.builder().config(conf).getOrCreate()
    sparkSession.sparkContext.setLogLevel("WARN")
    val sqlContext = sparkSession.sqlContext
    val schemaFields = fields.map(x => StructField(x.toLowerCase, StringType))
    val structType = StructType(schemaFields)
    val df = sqlContext.read.schema(structType).json("file:///C:\\Users\\gaodaibin\\Desktop\\NFV\\json\\nfv-test.json")
    df.createOrReplaceTempView("test")
    val sql = "select starttime, eventtime,to_unix_timestamp(starttime, 'yyyy-MM-dd HH:mm:ss') as stime, to_unix_timestamp(eventtime, 'yyyy-mm-dd hh:mm:ss') as etime from test"
    val resDF = sparkSession.sqlContext.sql(sql)
    resDF.show(3)
    sparkSession.stop()
  }
}

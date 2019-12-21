package com.github.json.readerer.guseynov

import org.apache.spark.sql.SparkSession
import org.json4s._
import org.json4s.jackson.JsonMethods._

object JsonReader extends App {

  val spark = SparkSession.builder().master("local").getOrCreate()

  val sparkContext = spark.sparkContext

  val fileName = args(0)

  implicit val formats = DefaultFormats

  sparkContext
    .textFile(fileName)
    .map(row => parse(row))
    .map(json => json.extract[Wine])
    .foreach(wine => println(wine))

  sparkContext.stop()
}

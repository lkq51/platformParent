package com.platform

import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ArrayBuffer

object Test {

	def main(args: Array[String]): Unit = {
			val conf: SparkConf = new SparkConf().setMaster("local[*]")
  			.setAppName("test")
			val sc: SparkContext = new SparkContext(conf)

			val str1 = new ArrayBuffer[String]()
			str1 += "one-1-abc"
			str1 += "two-2-abc"
			str1 += "three-3-abc"
			str1 += "three-3-abc"
			str1 += "three-3-abc"
			str1 += "three-4-abc"

			val words = sc.parallelize(str1).map(x => {
				(x.split("-")(0), x.split("-")(1), x.split("-")(2))
			})
			words.foreach(println)
			val wordSum = words.map(res => (res._1, 1)).reduceByKey(_ + _)
		  val wordAndColorSum = words.map(res => (res._1 + "-" + res._2, (1, List(res))))
			  .reduceByKey((x, y) => {
				  (x._1 + y._1, x._2 ++: y._2)
			  })
			  .map(res => (res._1.split("-")(0), (res._2._1 ,res._2._2)))
		println("wordSum size : " + wordSum.count())
		wordSum.foreach(println)
		println("wordAndColorSum size :" + wordAndColorSum.count())
		wordAndColorSum.foreach(println)
		val result =	wordAndColorSum.join(wordSum).filter(res => {
				res._2._1._1 > res._2._2 * 0.5
			}).map(x => {
				x._2._1._2
			}).flatMap( x => x)
		result.foreach(println)
	}

}

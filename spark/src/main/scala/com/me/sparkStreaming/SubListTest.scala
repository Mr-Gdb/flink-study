package com.me.sparkStreaming

object SubListTest {
  def main(args: Array[String]): Unit = {
    val lst = List(1,2,3,4,5,6,7,8,9,10)
    val generator = SubListGenerator(1L, lst)
    while (generator.hasNext()) {
      println(generator.next())
    }
  }
}


case class SubListGenerator(timestamp: Long, seq: List[Int]) {
  val MAX_LENGTH = 3
  var start = 0
  val end = seq.length

  def hasNext(): Boolean = {
    if (start < end) true else false
  }

  def next(): List[Int] = {
    val res = if (end - start > MAX_LENGTH) {
      seq.slice(start, start + MAX_LENGTH)
    } else {
      seq.slice(start, end)
    }
    start += MAX_LENGTH
    res
  }
}

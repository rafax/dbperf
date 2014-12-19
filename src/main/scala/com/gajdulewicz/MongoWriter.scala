package com.gajdulewicz

import com.gajdulewicz.Models.Profile

class MongoWriter(connectionString: String = "localhost:27017", dbName: String = "perftest", bufferSize: Int = 100000) {

  import com.mongodb.casbah.Imports._

  val profiles = MongoClient(connectionString)(dbName)("profiles")
  profiles.drop()
  var flushed = 0
  var pending = 0
  var writes = collection.mutable.MutableList[MongoDBObject]()
  var start = System.nanoTime

  def scheduleForInsert(obj: Profile) {
    writes += obj.toMongoObject
    pending += 1
    if (pending == bufferSize) {
      time("Mongo write took") {
        profiles.insert(writes: _*)
      }
      writes.clear()
      flushed += pending
      pending = 0
      println("Flushed " + flushed + " in " + (System.nanoTime - start) / 1000 / 1000)
      start = System.nanoTime
      if (flushed % (bufferSize * 10) == 0) {
        System.gc()
      }
    }

  }


  def time[A](message: String = "")(a: => A) = {
    val now = System.nanoTime
    val result = a
    val micros = (System.nanoTime - now) / 1000 / 1000
    println(message + " %d ms".format(micros))
    result
  }
}

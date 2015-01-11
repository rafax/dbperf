package com.gajdulewicz

import com.gajdulewicz.Models.Profile
import com.mongodb.WriteConcern
import com.mongodb.casbah.commons.MongoDBObject

class MongoWriter(connectionString: String = "localhost:27017", dbName: String = "perftest", bufferSize: Int = 100000, writeConcenrn: WriteConcern = WriteConcern.ACKNOWLEDGED, verbose: Boolean = false) {

  implicit def profileConverter(prof: Profile) = {
    MongoDBObject("_id" -> prof.id, "brandId" -> prof.brandId,
      "emailProfile" -> prof.emailProfile.map(e => MongoDBObject("_id" -> e.email, "email" -> e.email, "passwordHash" -> e.passwordHash)),
      "fbProfile" -> prof.fbProfile.map(f => MongoDBObject("_id" -> f.id, "token" -> f.token)),
      "twitterProfile" -> prof.twitterProfile.map(t => MongoDBObject("_id" -> t.id, "token" -> t.token)))
  }

  import com.mongodb.casbah.Imports._

  val profiles = MongoClient(connectionString)(dbName)("profiles")
  profiles.setWriteConcern(writeConcenrn)
  profiles.drop()
  var flushed = 0
  var pending = 0
  var writes = collection.mutable.MutableList[MongoDBObject]()
  var start = System.nanoTime

  def scheduleForInsert(obj: Profile) {
    writes += profileConverter(obj)
    pending += 1
    if (pending == bufferSize) {
      if(verbose)
        time("Mongo write took") {
          profiles.insert(writes :_*)
        }
      else
        profiles.insert(writes :_*)
      writes.clear()
      flushed += pending
      pending = 0
      if(verbose)
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

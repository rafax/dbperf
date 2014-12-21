package com.gajdulewicz

import com.mongodb.casbah.WriteConcern

/**
 * Created by gajduler on 09/12/14.
 */
object GeneratorApp {
  def main(args: Array[String]) {

    val writes: Int = 1 * 100 * 1000

    val initWriter = new MongoWriter(bufferSize = 100000)
    (1 to writes).foreach(id => {
      val prof = ProfileGenerator.genProfile(id)
      initWriter.scheduleForInsert(prof)
    })

    val defaultWriter = new MongoWriter(bufferSize = 100000)

    time("Default concern") {
      (1 to writes).foreach(id => {
        val prof = ProfileGenerator.genProfile(id)
        defaultWriter.scheduleForInsert(prof)
      })
    }

    val journaledWriter = new MongoWriter(bufferSize = 100000, writeConcenrn = WriteConcern.Journaled)

    time("Journaled concern") {
      (1 to writes).foreach(id => {
        val prof = ProfileGenerator.genProfile(id)
        journaledWriter.scheduleForInsert(prof)
      })
    }

    val fsynced = new MongoWriter(bufferSize = 100000, writeConcenrn = WriteConcern.Fsynced)

    time("fsynced concern") {
      (1 to writes).foreach(id => {
        val prof = ProfileGenerator.genProfile(id)
        fsynced.scheduleForInsert(prof)
      })
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

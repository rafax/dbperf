package com.gajdulewicz

/**
 * Created by gajduler on 09/12/14.
 */
object GeneratorApp {
  def main(args: Array[String]) {

    val writer =  new MongoWriter(bufferSize = 100000)

    (1 to 100 * 1000 * 1000).foreach(id => {
      val prof = ProfileGenerator.genProfile(id)
      writer.scheduleForInsert(prof)
    })


  }

}

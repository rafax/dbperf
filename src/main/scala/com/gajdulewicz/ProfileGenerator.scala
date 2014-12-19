package com.gajdulewicz

import akka.actor.{Actor, ActorRef}
import com.gajdulewicz.Models.{EmailAuth, FbAuth, Profile, TwitterAuth}

import scala.util.Random

/**
 * Created by gajduler on 10/12/14.
 */

object ProfileGenerator {
  val r = Random

  def apply(s: ActorRef) = new ProfileGenerator(s)

  def genProfile(id: Int, emailFbTwitterProbability: Seq[Double] = Seq(0.4, 0.3, 0.3)) = {
    val authSelector = r.nextDouble()
    val emailAuth =
      if (authSelector < emailFbTwitterProbability(0)) Some(EmailAuth(r.nextString(30), r.nextString(20))) else None
    val fbAuth =
      if (authSelector < emailFbTwitterProbability(0)) Some(FbAuth(r.nextLong(), r.nextLong())) else None
    val twitterAuth =
      if (authSelector > emailFbTwitterProbability(0)) Some(TwitterAuth(r.nextLong(), r.nextLong())) else None
    val profile = Profile(id, brandId = r.nextInt(50), emailAuth, fbAuth, twitterAuth)
    profile
  }
}

class ProfileGenerator(sendTo: ActorRef) extends Actor {

  def receive = {
    case id: Int =>
      val profile = ProfileGenerator.genProfile(id)
      sendTo ! profile
  }

}

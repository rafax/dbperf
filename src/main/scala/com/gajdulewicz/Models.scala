package com.gajdulewicz

/**
 * Created by gajduler on 10/12/14.
 */


case object Models {

  case class FbAuth(id: Long, token: Long)

  case class TwitterAuth(id: Long, token: Long)

  case class EmailAuth(email: String, passwordHash: String)

  case class Profile(id: Long, brandId: Long, emailProfile: Option[EmailAuth], fbProfile: Option[FbAuth], twitterProfile: Option[TwitterAuth])

}

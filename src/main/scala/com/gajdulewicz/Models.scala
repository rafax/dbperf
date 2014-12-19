package com.gajdulewicz

import com.mongodb.casbah.Imports.MongoDBObject

/**
 * Created by gajduler on 10/12/14.
 */


case object Models {

  case class FbAuth(id: Long, token: Long) {
    def toMongoObject = {
      MongoDBObject("_id" -> id, "token" -> token)
    }
  }

  case class TwitterAuth(id: Long, token: Long) {
    def toMongoObject = {
      MongoDBObject("_id" -> id, "token" -> token)
    }
  }

  case class EmailAuth(email: String, passwordHash: String) {
    def toMongoObject = {
      MongoDBObject("_id" -> email, "email" -> email, "passwordHash" -> passwordHash)
    }
  }

  case class Profile(id: Long, brandId: Long, emailProfile: Option[EmailAuth], fbProfile: Option[FbAuth], twitterProfile: Option[TwitterAuth]) {
    def toMongoObject = {
      MongoDBObject("_id" -> id, "brandId" -> brandId, "emailProfile" -> emailProfile.map(_.toMongoObject), "fbProfile" -> fbProfile.map(_.toMongoObject), "twitterProfile" -> twitterProfile.map(_.toMongoObject))
    }
  }

}

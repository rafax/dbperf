name := "dbperf"

version := "1.0"

scalaVersion := "2.11.4"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.1" % "test"

libraryDependencies += "org.mockito" % "mockito-core" % "1.10.8" % "test"

libraryDependencies ++= Seq("org.mongodb" %% "casbah" % "2.7.4",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0",
  "org.mongodb" % "casbah-core_2.11" % "2.7.4"
)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.3.7"
)
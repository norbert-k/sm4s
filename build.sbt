name := "sm4s"

organization := "com.github.nkyian"

version := "0.0.1"

licenses := Seq("APL2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt"))

scalaVersion := "2.12.8"

description := "System Metrics library"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.23",
  "com.typesafe.akka" %% "akka-stream" % "2.5.23",
  "com.github.oshi" % "oshi-core" % "3.13.2",
  "net.java.dev.jna" % "jna" % "5.3.1",
  "org.slf4j" % "slf4j-api" % "1.7.5",
  "org.slf4j" % "slf4j-simple" % "1.7.5",
)
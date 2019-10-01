name := "impes"

version := "0.1"

scalaVersion := "2.12.9"

val sparkVersion = "2.4.3"
val akkaVersion = "2.5.25"
val akkaHttpVersion = "10.1.9"

scalacOptions += "-Ypartial-unification"


libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,

  "mysql" % "mysql-connector-java" % "8.0.17",

  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,

  "com.typesafe.slick" %% "slick" % "3.2.0",

  "org.xerial" % "sqlite-jdbc" % "3.16.1",
  "org.mindrot"  % "jbcrypt"   % "0.4",
  "com.github.dwickern" %% "scala-nameof" % "1.0.3" % "provided",
  "org.typelevel" %% "cats-core" % "2.0.0",
  "org.typelevel" %% "cats-macros" % "2.0.0",
  "org.typelevel" %% "cats-kernel" % "2.0.0",
)

name := """demoPF2"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "org.mongodb" % "mongodb-driver-async" % "3.2.2",
  "commons-io" % "commons-io" % "2.5",
  "com.feth" %% "play-authenticate" % "0.8.3"
)

routesGenerator := InjectedRoutesGenerator

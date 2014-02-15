name := "tape-client"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  "io.netty" % "netty-all" % "4.0.15.Final"
)

play.Project.playScalaSettings

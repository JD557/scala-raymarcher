name := "RayMarcher"

version := "1.0"

scalaVersion := "2.12.10"

libraryDependencies ++= List(
  "org.apache.commons" %  "commons-math3" % "3.6.1",
  "eu.joaocosta"       %% "minart-core"   % "0.1.0"
)

scalacOptions ++= List("-opt:l:classpath")

name := "RayMarcher"

version := "1.0"

scalaVersion := "2.12.1"

libraryDependencies ++= List("org.apache.commons" % "commons-math3" % "3.6.1")

scalacOptions ++= List("-opt:l:classpath")

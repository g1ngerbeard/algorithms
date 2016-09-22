name := "algorithms"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.apache.commons" % "commons-lang3" % "3.4",
  "com.google.guava" % "guava" % "19.0",
  "org.scalatest" %% "scalatest" % "3.0.0" % Test,
  "junit" % "junit" % "4.11" % Test
//  "com.novocode" % "junit-interface" % "0.11" % Test
//    exclude("junit", "junit-dep")
)

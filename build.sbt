name := "testPlay"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "mysql" % "mysql-connector-java" % "5.1.26",
  "org.hibernate" % "hibernate-entitymanager" % "4.3.1.Final"
)     

play.Project.playJavaSettings

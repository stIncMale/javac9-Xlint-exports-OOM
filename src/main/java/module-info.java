module stinc.male.server {
  requires transitive jsr305;
  requires com.google.common;
  requires java.dogstatsd.client;
  exports mypackage;
}
//javac -J-Xmx1000M -Xlint:exports,-requires-automatic,-requires-transitive-automatic --module-path /Users/male/.m2/repository/com/google/guava/guava/23.2-jre/guava-23.2-jre.jar:/Users/male/.m2/repository/com/datadoghq/java-dogstatsd-client/2.3/java-dogstatsd-client-2.3.jar:/Users/male/.m2/repository/com/google/code/findbugs/jsr305/3.0.2/jsr305-3.0.2.jar ./src/main/java/module-info.java ./src/main/java/mypackage/MyClass.java
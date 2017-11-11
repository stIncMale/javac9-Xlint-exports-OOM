# javac9-Xlint-exports-OOM
A minimal working example that reproduces
```javac 9.0.1``` (```Java HotSpot(TM) 64-Bit Server VM (build 9.0.1+11, mixed mode)``` on ```masOS Sierra 10.12.6```)
```OutOfMemoryError``` caused by ```-Xlint:exports``` option. This problem has beed reported to Oracle with internal review ```ID : 9051540```.  

Before reproducing one need to download the required external dependencies:
https://repo1.maven.org/maven2/com/google/code/findbugs/jsr305/3.0.2/jsr305-3.0.2.jar
https://repo1.maven.org/maven2/com/google/guava/guava/23.3-jre/guava-23.3-jre.jar
https://repo1.maven.org/maven2/com/datadoghq/java-dogstatsd-client/2.3/java-dogstatsd-client-2.3.jar

Execute ```javac``` from the root directory of the project in order to reproduce the problem (you may need to change paths to the downloaded JARs):
```
javac -J-Xmx6G -Xlint:exports,-requires-automatic,-requires-transitive-automatic --module-path /Users/male/.m2/repository/com/google/guava/guava/23.3-jre/guava-23.3-jre.jar:/Users/male/.m2/repository/com/datadoghq/java-dogstatsd-client/2.3/java-dogstatsd-client-2.3.jar:/Users/male/.m2/repository/com/google/code/findbugs/jsr305/3.0.2/jsr305-3.0.2.jar ./src/main/java/module-info.java ./src/main/java/mypackage/MyClass.java
```
Problem goes away if ```-Xlint:exports``` is disabled (note ```-J-Xmx100M``` instead of ```-J-Xmx6G```):
```
javac -J-Xmx100M -Xlint:-exports,-requires-automatic,-requires-transitive-automatic --module-path /Users/male/.m2/repository/com/google/guava/guava/23.3-jre/guava-23.3-jre.jar:/Users/male/.m2/repository/com/datadoghq/java-dogstatsd-client/2.3/java-dogstatsd-client-2.3.jar:/Users/male/.m2/repository/com/google/code/findbugs/jsr305/3.0.2/jsr305-3.0.2.jar ./src/main/java/module-info.java ./src/main/java/mypackage/MyClass.java
```
It also goes away if ```-Xlint:exports``` is enabled, but a completely unnecessary ```guava-23.3-jre.jar``` is removed from ```--module-path```:
```
javac -J-Xmx100M -Xlint:exports,-requires-automatic,-requires-transitive-automatic --module-path /Users/male/.m2/repository/com/datadoghq/java-dogstatsd-client/2.3/java-dogstatsd-client-2.3.jar:/Users/male/.m2/repository/com/google/code/findbugs/jsr305/3.0.2/jsr305-3.0.2.jar ./src/main/java/module-info.java ./src/main/java/mypackage/MyClass.java
```

Option ```-Xlint:exports``` is very important because it tells us when we need to specify ```requires transitive```, which is virtually
impossible to do by hand. I understand that not everything is exactly optimal in the source code of this example and the way ```javac``` is used,
but that is the point, javac must tell us if it is still OK, or if something is not right.
It however not only fails to tell us anything, but instead throws ```OutOfMemoryError``` even when it has 6GB Java heap size available.

As a workaround in projects we can:
* Replace all ```requires transitive``` with ```requires``` in modules
and get a list of all ```warning: [exports] ... is not indirectly exported using requires transitive``` produced by ```-Xlint:exports``` option.
* Fix the warnings by adding only the optimally required ```requires transitive``` in modules.

All content is licensed under [![WTFPL logo](http://www.wtfpl.net/wp-content/uploads/2012/12/wtfpl-badge-2.png)](http://www.wtfpl.net/),
except where another license is explicitly specified.
mkdir -p build/classes

javac -cp "src/" -d build/classes src/proglab/Main.java
jar cfm build/lab.jar META-INF/MANIFEST.MF -C build/classes/ .
java -jar build/lab.jar

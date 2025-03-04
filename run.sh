mkdir -p build/classes

javac -cp "src/" -d build/classes src/proglab5/Main.java
jar cfm build/lab5.jar META-INF/MANIFEST.MF -C build/classes/ .
java -jar build/lab5.jar

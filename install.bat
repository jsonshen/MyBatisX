@echo off
set JAVA_HOME=%JAVA8_HOME%
set PATH=%JAVA_HOME%/bin;%PATH%
java -version
javac -version
mvn clean install -pl core,generator -am
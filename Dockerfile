FROM openjdk:8-jdk-alpine
MAINTAINER baeldung.com
COPY target/hatdau-0.0.1-SNAPSHOT.jar hatdau-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/hatdau-0.0.1-SNAPSHOT.jar"]
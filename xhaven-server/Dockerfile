FROM openjdk:17-jdk-alpine AS build
EXPOSE 8080
COPY ./target/xhaven-server-0.0.1-SNAPSHOT.jar xhaven-server.jar
ENTRYPOINT ["java","-jar","/xhaven-server.jar"]
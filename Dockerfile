FROM maven:3.6.0-jdk-11-slim AS build
COPY src ../apijava/src
COPY pom.xml ../apijava/
RUN mvn clean install -DskipTests
RUN mvn -f ../apijava/pom.xml clean package

FROM adoptopenjdk/openjdk11:alpine-jre
EXPOSE 8080
COPY src/main/resources/application.properties /app/src/main/resources/application.properties
ADD target/api-springboot-0.0.1.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
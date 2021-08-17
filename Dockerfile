FROM openjdk:8-jdk-alpine

WORKDIR /app

COPY app.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]

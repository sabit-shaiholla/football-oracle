# Backend Dockerfile
FROM openjdk:17-jdk-alpine
LABEL authors="reddevil06"
WORKDIR /app
COPY target/football-oracle-0.0.1-SNAPSHOT.jar /app/football-oracle-backend.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/football-oracle-backend.jar"]
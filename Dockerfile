FROM maven:3.9.8-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM docker.io/library/openjdk:21
WORKDIR /app
COPY --from=builder /app/target/delivery-0.0.1-SNAPSHOT.jar delivery.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "delivery.jar"]
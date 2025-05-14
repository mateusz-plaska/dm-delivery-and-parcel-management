FROM docker.io/library/openjdk:21
WORKDIR /app
COPY /target/delivery-0.0.1-SNAPSHOT.jar delivery.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "delivery.jar"]
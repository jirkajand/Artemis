# Build stage
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY notification-service/pom.xml /app/notification-service/

COPY api/ /app/api/

COPY notification-service/src/ /app/notification-service/src/

WORKDIR /app/notification-service
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/notification-service/target/notification-service-0.0.1-SNAPSHOT.jar app.jar
ARG NOTIFICATION_SERVICE_PORT
ENV SERVER_PORT=${NOTIFICATION_SERVICE_PORT}
EXPOSE ${SERVER_PORT}
ENTRYPOINT ["java", "-jar", "app.jar"]
# Build stage
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Kopírujeme pouze pom.xml do složky settings-service
COPY settings-service/pom.xml /app/settings-service/

# Kopírujeme OpenAPI specifikace
COPY api/ /app/api/

# Kopírujeme zdrojové soubory
COPY settings-service/src/ /app/settings-service/src/

# Přesuneme se do adresáře s projektem a spustíme build
WORKDIR /app/settings-service
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/settings-service/target/settings-service-0.0.1-SNAPSHOT.jar app.jar
ARG SETTINGS_SERVICE_PORT
ENV SERVER_PORT=${SETTINGS_SERVICE_PORT}
EXPOSE ${SERVER_PORT}
ENTRYPOINT ["java", "-jar", "app.jar"]
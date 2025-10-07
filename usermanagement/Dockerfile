# Build stage
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Kopírujeme pouze pom.xml do složky usermanagement
COPY usermanagement/pom.xml /app/usermanagement/

# Kopírujeme OpenAPI specifikace
COPY api/ /app/api/

# Kopírujeme zdrojové soubory
COPY usermanagement/src/ /app/usermanagement/src/

# Přesuneme se do adresáře s projektem a spustíme build
WORKDIR /app/usermanagement
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/usermanagement/target/usermanagement-0.0.1-SNAPSHOT.jar app.jar
ARG USER_MANAGEMENT_PORT
ENV SERVER_PORT=${USER_MANAGEMENT_PORT}
EXPOSE ${SERVER_PORT}
ENTRYPOINT ["java", "-jar", "app.jar"]
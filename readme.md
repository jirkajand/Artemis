## Run Artemis

There is need to get keycloak client secret and set it in `.env` file before running the docker compose.

To obtain the client secret, follow these steps:

1. Open the Keycloak admin console in your web browser.
2. Log in with your admin credentials.
3. Navigate to correct realm (e.g., `Artemis`).
4. Go to the "Clients" section in the left-hand menu.
5. Select the client for which you need (e.g., `artemisBE-admin-cli`).
6. Go to the "Credentials" tab.
7. Copy the value of the "Secret" field.
8. Open the `.env` file in the root directory of the Artemis project.

## Open Api Artemis
There is one file with OpenAPI specification for all microservices: `openapi.yaml`.
It is used to generate API documentation and client SDKs.

### Backend Code Generation
For backend there is need to use the following command to generate models and API interfaces:
```bash
mvn clean compile
```
This will generate code for each microservice based on the `openapi.yaml` file and the tag of each api specification.

- User Management Service - tag: `UserManagement`

### Frontend Code Generation

**TODO**

### API Documentation


Url for Health Check is:
```
http://localhost:8080/user-management/api/health
```

The api should be in format `localhost:${GATEWAY_PORT}/{microservice-name}/api/...`

## Backend Development

### Rerun docker compose

Restart for docker compose to run new BE builds

```bash
# stop current build
docker compose down

# setup new build
docker compose up --build -d
```

### How to add new microservice

1. Right click on the Artemis project and create create "new module" (Spring Boot) for your microservice.
   1. Setup the new module with dependencies, check existing microservices for reference.
   2. Make sure to set the port for your microservice in `application.properties` file.
   3. Make sure to set correct the openapi generator plugin in `pom.xml` file.
2. Add your microservice code and a `Dockerfile` in that folder.
3. Update the `docker-compose.yml` file to include your new microservice.
   1. Update also command for `nginx` service to wait for new microservice to be ready.
   2. Update also command for replace variables in `nginx.conf` file.
4. Update the `nginx.conf` file to route requests to your new microservice.
5. Update the `.env` file with new microservice port and other necessary environment variables.
   6. Also update `DB_NAMES` to add new database name if your microservice needs a database.
6. Restart the Docker Compose setup using the commands above.
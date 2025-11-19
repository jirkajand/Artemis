# https://gist.github.com/aradalvand/04b2cad14b00e5ffe8ec96a3afbb34fb
# https://dev.to/code42cate/how-to-dockerize-sveltekit-3oho
# https://stackoverflow.com/questions/26220957/how-can-i-inspect-the-file-system-of-a-failed-docker-build

# 1) build stage
FROM node:20-bookworm AS builder
WORKDIR /app

# set env variables
ARG VITE_API_BASE_URL
ARG VITE_KEYCLOAK_URL
ARG VITE_KEYCLOAK_REALM
ARG VITE_KEYCLOAK_CLIENT_ID
ENV VITE_API_BASE_URL=$VITE_API_BASE_URL
ENV VITE_KEYCLOAK_URL=$VITE_KEYCLOAK_URL
ENV VITE_KEYCLOAK_REALM=$VITE_KEYCLOAK_REALM
ENV VITE_KEYCLOAK_CLIENT_ID=$VITE_KEYCLOAK_CLIENT_ID

# install java (for openapi-generator-cli)
RUN apt-get update && apt-get install -y openjdk-17-jre
COPY frontend ./frontend
COPY api ./api
WORKDIR /app/frontend

RUN npm ci
# Pre-download the generator jar (avoids timeout issues)
RUN npx @openapitools/openapi-generator-cli version-manager set 7.16.0
RUN npm run generate:api
RUN npm run build
RUN npm prune --production

# 2) runtime stage (Static server)
FROM nginx:alpine
COPY --from=builder /app/frontend/build /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]

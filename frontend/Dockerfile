# https://gist.github.com/aradalvand/04b2cad14b00e5ffe8ec96a3afbb34fb
# https://dev.to/code42cate/how-to-dockerize-sveltekit-3oho
# https://stackoverflow.com/questions/26220957/how-can-i-inspect-the-file-system-of-a-failed-docker-build

# build stage
FROM node:20-bookworm AS builder
WORKDIR /app
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

# runtime stage (Static server)
FROM nginx:alpine
COPY --from=builder /app/frontend/build /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]

# # runtime stage (Node server)
# FROM node:20-alpine
# WORKDIR /app
# COPY --from=builder /app/build build/
# COPY --from=builder /app/node_modules node_modules/
# COPY package.json .
# EXPOSE 3000
# ENV NODE_ENV=production
# CMD [ "node", "build" ]

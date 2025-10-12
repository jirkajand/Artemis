# https://gist.github.com/aradalvand/04b2cad14b00e5ffe8ec96a3afbb34fb
# https://dev.to/code42cate/how-to-dockerize-sveltekit-3oho

# build stage
FROM node:20-alpine AS builder
WORKDIR /app
COPY package*.json .
RUN npm ci
COPY . .
RUN npm run build
RUN npm prune --production

# # runtime stage (Node server)
# FROM node:20-alpine
# WORKDIR /app
# COPY --from=builder /app/build build/
# COPY --from=builder /app/node_modules node_modules/
# COPY package.json .
# EXPOSE 3000
# ENV NODE_ENV=production
# CMD [ "node", "build" ]
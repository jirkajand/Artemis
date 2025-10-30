FROM postgres:13

COPY devops/init-multiple-db.sh /docker-entrypoint-initdb.d/init-multiple-db.sh

# Fix Windows CRLF line endings
RUN sed -i 's/\r$//' /docker-entrypoint-initdb.d/init-multiple-db.sh \
    && chmod +x /docker-entrypoint-initdb.d/init-multiple-db.sh
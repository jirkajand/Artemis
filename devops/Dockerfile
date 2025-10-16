FROM postgres:13

COPY devops/init-multiple-db.sh /docker-entrypoint-initdb.d/init-multiple-db.sh

RUN chmod +x /docker-entrypoint-initdb.d/init-multiple-db.sh
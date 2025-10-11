#!/bin/bash
set -e

until pg_isready -U "$POSTGRES_USER" -h "localhost" -d "$POSTGRES_DB"; do
  echo "⏳ Waiting for Postgres SQL ..."
  sleep 2
done

IFS=',' read -ra DBS <<< "$DB_NAMES"

for DB in "${DBS[@]}"; do
  DB=$(echo "$DB" | xargs)
  echo "📦 Checking DB: $DB"
  psql -v ON_ERROR_STOP=1 -U "$POSTGRES_USER" -d "$POSTGRES_DB" <<-EOSQL
    SELECT 'CREATE DATABASE $DB'
    WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = '$DB')\gexec
    GRANT ALL PRIVILEGES ON DATABASE $DB TO $POSTGRES_USER;
EOSQL
done

echo "✅ All databases are ready."

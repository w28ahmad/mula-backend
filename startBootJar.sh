#!/bin/sh

source '.env'

echo "Connecting to host ${DB_HOST}..."

java -jar \
  -Dspring.profiles.active=dev \
  -DDB_HOST=$DB_HOST -DDB_PORT=$DB_PORT \
  -DDB_NAME=$DB_NAME -DDB_USER=$DB_USER \
  -DDB_USER=$DB_USER -DDB_PASSWORD=$DB_PASSWORD \
  ./build/libs/mula-0.0.1-SNAPSHOT.jar
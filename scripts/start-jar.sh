#!/bin/bash

cd .. || exit 1

echo "Build jar"
./gradlew clean bootJar

echo "Start postgres"
cd infrastructure || exit 1
docker-compose up -d postgres-server

sleep 10

cd ..
mkdir -p logs

echo "Start application"
nohup java -jar \
  application/build/libs/application.jar > logs/application.log 2>&1 &

echo $! > application.pid

echo "Application started in detached mode. PID: $(cat application.pid)"
echo "Logs are available in logs/application.log"
#!/bin/bash

cd .. || exit 1

echo "Build jar"
./gradlew clean jibDockerBuild

echo "Start application"
cd infrastructure || exit 1
docker-compose up -d

echo "Application started in detached mode."

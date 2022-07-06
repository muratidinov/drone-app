#!/usr/bin/env bash
mvn clean package
# docker rm -f $(docker ps -aq)
docker-compose up --build
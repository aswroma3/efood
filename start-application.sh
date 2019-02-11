#!/bin/bash

if [ -z "$DOCKER_HOST_IP" ] ; then
    echo "please do source set-docker-host-ip.sh"
    export DOCKER_HOST_IP=10.11.1.188
fi

# Script per avviare l'applicazione con Docker Compose

echo Starting the application...

# docker-compose up -d --build mysql
docker-compose up -d zookeeper kafka
docker-compose up -d postgres

# ./wait-for-mysql.sh
./wait-for-postgres.sh

docker-compose up -d --build

echo -n Waiting for the services to start...

./wait-for-services.sh

# docker stack deploy --compose-file docker-compose.yml asw-ms

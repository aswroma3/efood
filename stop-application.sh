#!/bin/bash

# Script per arrestare l'applicazione con Docker Compose

echo Halting the application
docker-compose down -v

# docker stack rm asw-ms

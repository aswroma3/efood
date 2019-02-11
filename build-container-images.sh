#!/bin/bash

# Script per effettuare la creazione delle immagini Docker con Compose

echo Building all images
docker-compose build

# echo Pushing all images to registry
# docker-compose push

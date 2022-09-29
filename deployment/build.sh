#!/usr/bin/env bash

mvn clean package -Dmaven.test.skip=true

echo "Building Docker image"
docker build -t playground/book-store-full:latest -f deployment/full/Dockerfile .
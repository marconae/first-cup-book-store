#!/usr/bin/env bash

mvn clean package -Dmaven.test.skip=true

echo "Building micro"
docker build -t playground/book-store-micro:latest -f Dockerfile .

echo "Building full"
docker build -t playground/book-store-full:latest -f deployment/full/Dockerfile .
#!/usr/bin/env bash

aws ecr get-login-password --region eu-central-1 | docker login --username AWS --password-stdin 964739173525.dkr.ecr.eu-central-1.amazonaws.com

docker tag playground/book-store-micro:latest 964739173525.dkr.ecr.eu-central-1.amazonaws.com/playground/book-store-micro:latest

docker push 964739173525.dkr.ecr.eu-central-1.amazonaws.com/playground/book-store-micro:latest
#!/usr/bin/env bash

REGION=eu-central-1
SERVICE_NAME=book-store-service-1

ecs-cli compose --project-name ${SERVICE_NAME} --file book-store-task.yml --ecs-params ecs-params.yml --region ${REGION} service up --create-log-groups
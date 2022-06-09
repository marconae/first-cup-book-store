#!/usr/bin/env bash

REGION=eu-central-1
TASK_NAME=book-store-cli

ecs-cli compose --project-name ${TASK_NAME} --file book-store-task.yml --ecs-params ecs-params.yml --region ${REGION} create --launch-type EC2
#!/usr/bin/env bash

STACK_NAME=book-store-task-revision-1

aws cloudformation create-stack --stack-name ${STACK_NAME} \
  --template-body file://task-stack.yml \
  --parameters ParameterKey=DockerImageTag,ParameterValue=latest ParameterKey=Family,ParameterValue=book-store
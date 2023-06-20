#!/bin/bash

# Build the Docker image
docker build -t democracia2tests -f ./docker_tests/Dockerfile .

# Run the Docker container and execute unit tests
docker run --rm democracia2tests mvn test

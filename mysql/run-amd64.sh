#!/bin/bash

cd "$(dirname "$0")"

export PORT=3307
docker-compose -f docker-compose-amd64.yml up -d

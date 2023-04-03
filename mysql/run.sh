#!/bin/bash

cd "$(dirname "$0")"

export PORT=3307
docker-compose up -d

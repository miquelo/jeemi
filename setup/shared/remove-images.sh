#!/bin/bash

docker rmi jeemi/agent-ssh
docker rmi jeemi/glassfish-v5-ssh
docker rmi jeemi/glassfish-v5
docker rmi jeemi/debian-ssh
docker rmi jeemi/debian

rm -rf image/package

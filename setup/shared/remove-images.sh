#!/bin/bash

docker rmi jeemi/agent
docker rmi jeemi/apache2-lb
docker rmi jeemi/glassfish-v5-executor
docker rmi jeemi/glassfish-v5-das
docker rmi jeemi/debian-ssh
docker rmi jeemi/debian

rm -rf image/package

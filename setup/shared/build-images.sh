#!/bin/bash

mkdir -p image/package

if [ ! -f image/package/glassfish-5.0.zip ]
then
    wget -P image/package \
    http://download.oracle.com/glassfish/5.0/release/glassfish-5.0.zip
fi

docker build \
-t jeemi/debian:latest \
-f image/debian.dockerfile \
image

docker build \
-t jeemi/debian-ssh:latest \
-f image/debian-ssh.dockerfile \
image

docker build \
-t jeemi/glassfish-v5-das:latest \
-f image/glassfish-v5-das.dockerfile \
image

docker build \
-t jeemi/glassfish-v5-executor:latest \
-f image/glassfish-v5-executor.dockerfile \
image

docker build \
-t jeemi/apache2-lb:latest \
-f image/apache2-lb.dockerfile \
image

docker build \
-t jeemi/agent:latest \
-f image/agent.dockerfile \
image

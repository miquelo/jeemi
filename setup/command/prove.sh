#!/bin/bash

# mvn -f module/jeemi/pom.xml install
# mvn -f module/jeemi-dist/pom.xml package

setup/shared/build-images.sh
setup/shared/build-certificates.sh

# -d
# -it --entrypoint /bin/bash
docker run \
-d \
--name jeemi-agent \
--hostname agent \
-p 2201:22 \
-p 8880:80 \
-p 4443:443 \
-p 24848:4848 \
-e "DOCKER_ADDRESS=/var/run/docker.sock" \
-v $HOME/.ssh/id_rsa.pub:/home/farmer/.ssh/authorized_keys.d/id_rsa.pub \
-v $PWD/setup/certificate/build/server/certs/jeemi.test.cert.pem:\
/home/farmer/lib/apache2/cert.pem \
-v $PWD/setup/certificate/build/server/private/jeemi.test.key.pem:\
/home/farmer/lib/apache2/key.pem \
jeemi/agent

read -r -p 'You can prove it now... [Press ENTER when finished]' _

# docker rm -f jeemi-agent-ssh
docker rm -f jeemi-agent


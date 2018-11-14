#!/bin/bash

# mvn -f module/jeemi/pom.xml install
# mvn -f module/jeemi-dist/pom.xml package

setup/shared/build-images.sh
setup/shared/build-certificates.sh

# -d
# -it --entrypoint /bin/bash
docker run \
-d \
--name jeemi-agent-ssh \
--hostname jeemi-agent \
-p 2201:22 \
-p 8011:80 \
-p 4431:443 \
-e "SSH_AUTHORIZED_KEYS=$(cat $HOME/.ssh/*.pub | (base64 -w 0 || base64))" \
-e "DOCKER_ADDRESS=/var/run/docker.sock" \
-e "APACHE2_CERTIFICATE=$(cat setup/certificate/build/server/certs/\
jeemi.test.cert.pem | (base64 -w 0 || base64))" \
-e "APACHE2_PRIVATE_KEY=$(cat setup/certificate/build/server/private/\
jeemi.test.key.pem | (base64 -w 0 || base64))" \
-v /var/run/docker.sock:/var/run/docker.sock \
jeemi/agent-ssh

read -r -p 'You can prove it now... [Press ENTER when finished]' _

docker rm -f jeemi-agent-ssh


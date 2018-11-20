#!/bin/sh

echo 'Preparing agent.'

echo "DOCKER_ADDRESS=$DOCKER_ADDRESS
export DOCKER_ADDRESS" >> \
/home/farmer/.bash_profile

if [ -f $DOCKER_ADDRESS ]
then
    chown farmer:farmer $DOCKER_ADDRESS
fi

su - farmer << EOF

echo 'worker.list=glassfish
worker.glassfish.type=ajp13
worker.glassfish.host=localhost
worker.glassfish.port=8009' > \
/home/farmer/lib/apache2/workers.properties

echo "
AS_ADMIN_MASTERPASSWORD=$(openssl rand -base64 32)
AS_ADMIN_PASSWORD=12345678" > \
passwords.txt

asadmin \
--interactive false \
--user admin \
--passwordfile passwords.txt \
create-domain \
--usemasterpassword true \
--savemasterpassword true \
jeemi-agent

asadmin \
--interactive false \
--passwordfile passwords.txt \
start-domain \
jeemi-agent

asadmin \
--interactive false \
--passwordfile passwords.txt \
enable-secure-admin

asadmin \
--interactive false \
--user admin \
--passwordfile passwords.txt \
set configs.config.server-config.network-config.network-listeners.\
network-listener.admin-listener.address=$(hostname -I)

asadmin \
--interactive false \
--user admin \
--passwordfile passwords.txt \
set configs.config.server-config.network-config.network-listeners.\
network-listener.http-listener-1.jk-enabled=true

asadmin \
--interactive false \
--user admin \
--passwordfile passwords.txt \
set configs.config.server-config.network-config.network-listeners.\
network-listener.http-listener-1.port=8009

asadmin \
--interactive false \
--passwordfile passwords.txt \
stop-domain \
jeemi-agent

asadmin \
--interactive false \
--passwordfile passwords.txt \
start-domain \
jeemi-agent

rm passwords.txt

EOF

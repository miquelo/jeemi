#!/bin/sh

echo 'worker.list=worker1
worker.worker1.type=ajp13
worker.worker1.host=localhost
worker.worker1.port=8009' > \
/usr/apache2/lib/workers.properties

echo '#!/bin/bash

echo "DOCKER_ADDRESS=$DOCKER_ADDRESS
export DOCKER_ADDRESS" >> \
/usr/lib/glassfish5/.bash_profile

if [ -f $DOCKER_ADDRESS ]
then
	chown glassfish:glassfish $DOCKER_ADDRESS
fi' > \
/root/bin/agent-setup.sh
chmod 0555 /root/bin/agent-setup.sh

echo '[program:agent-setup]
command=/root/bin/agent-setup.sh
startsecs=0
autorestart=false' \
> /etc/supervisor/conf.d/agent-setup.conf


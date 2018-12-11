#!/bin/sh

echo '#!/bin/bash

LIFECYCLE_LOG_FILE='/var/log/jeemi-lifecycle.log'

services_start() {
	service ssh start
	service apache2 start
	service jeemi-lifecycle start
}

services_stop() {
	service ssh stop
	service apache2 stop
	service jeemi-lifecycle stop
	trap - SIGINT SIGTERM
	exit
}

services_start
trap services_stop SIGINT SIGTERM

while [ ! -f $LIFECYCLE_LOG_FILE ]
do
	sleep 1
done
tail -f $LIFECYCLE_LOG_FILE' > \
/init
chmod 500 /init

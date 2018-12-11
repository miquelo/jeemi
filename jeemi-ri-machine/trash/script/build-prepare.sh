#!/bin/sh

apt-get -y update

mkdir /root/bin
mkdir /root/lib

echo '#!/bin/bash
mkdir -p /tmp/setup
if [ -n "$(ls -A /tmp/setup/*.sh 2> /dev/null)" ]
then
    for SETUP_SCRIPT in /tmp/setup/*.sh
    do
        sh $SETUP_SCRIPT >> /var/log/setup.log 1>> /var/log/setup.log
        rm $SETUP_SCRIPT
    done
fi
rm -r /tmp/setup' > \
/root/bin/setup.sh
chmod 500 /root/bin/setup.sh

touch /root/lib/service.list
chmod 0600 /root/lib/service.list

echo '#!/bin/bash
/root/bin/setup.sh

while read SERVICE_NAME
do
    service $SERVICE_NAME start >> /var/log/setup.log 1>> /var/log/setup.log
done < /root/lib/service.list
tail -f /var/log/setup.log' > \
/init
chmod 500 /init

mkdir /home/farmer
mkdir /home/farmer/bin
mkdir /home/farmer/lib

useradd \
-d /home/farmer \
-s /bin/bash \
farmer
echo 'PATH=$HOME/bin:$PATH
export PATH' > \
/home/farmer/.bash_profile
chown -R farmer:farmer /home/farmer


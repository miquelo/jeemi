#!/bin/sh

apt-get -y install openssh-server

SEARCH_PATTERN="#StrictModes yes"
REPLACE_PATTERN="StrictModes yes"
sed -ie "s/$SEARCH_PATTERN/$REPLACE_PATTERN/g" \
/etc/ssh/sshd_config

SEARCH_PATTERN="#PubkeyAuthentication yes"
REPLACE_PATTERN="PubkeyAuthentication yes"
sed -ie "s/$SEARCH_PATTERN/$REPLACE_PATTERN/g" \
/etc/ssh/sshd_config

mkdir -p /var/run/sshd
chmod 0755 /var/run/sshd

echo '#!/bin/bash

# Enable password authentication
if [ ! -z ${SSH_PASSWORD+x} ]
then
	echo "$SSH_USER:$SSH_PASSWORD" | chpasswd
fi

# Enable public key authentication
mkdir $(eval echo ~$SSH_USER)/.ssh
echo $SSH_AUTHORIZED_KEYS | base64 \
--decode > $(eval echo ~$SSH_USER)/.ssh/authorized_keys' > \
/root/bin/sshd-user-setup.sh
chmod 0555 /root/bin/sshd-user-setup.sh

echo '[program:sshd]
command=/usr/sbin/sshd -D' > \
/etc/supervisor/conf.d/sshd.conf

echo '[program:sshd-user-setup]
command=/root/bin/sshd-user-setup.sh
startsecs=0
autorestart=false' > \
/etc/supervisor/conf.d/sshd-user-setup.conf


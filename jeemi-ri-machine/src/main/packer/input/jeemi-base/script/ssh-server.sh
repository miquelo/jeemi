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

SEARCH_PATTERN="#AuthorizedKeysCommand none"
REPLACE_PATTERN="AuthorizedKeysCommand \/usr\/bin\/authkeys"
sed -ie "s/$SEARCH_PATTERN/$REPLACE_PATTERN/g" \
/etc/ssh/sshd_config

SEARCH_PATTERN="#AuthorizedKeysCommandUser nobody"
REPLACE_PATTERN="AuthorizedKeysCommandUser authkeys"
sed -ie "s/$SEARCH_PATTERN/$REPLACE_PATTERN/g" \
/etc/ssh/sshd_config

useradd -r -s /bin/false authkeys

echo '#!/bin/sh

SSH_DIR="~${1}/.ssh"
SSH_AUTHORIZED_KEYS="`eval echo $SSH_DIR/authorized_keys`"
SSH_AUTHORIZED_KEYS_DIR="`eval echo $SSH_DIR/authorized_keys.d/`"
test -f $SSH_AUTHORIZED_KEYS && \
cat $SSH_AUTHORIZED_KEYS
test -d $SSH_AUTHORIZED_KEYS_DIR && \
test ! -z "$(ls -A $SSH_AUTHORIZED_KEYS_DIR)" && \
cat $SSH_AUTHORIZED_KEYS_DIR/*' > \
/usr/bin/authkeys
chmod 0555 /usr/bin/authkeys

mkdir -p /var/run/sshd
chmod 0700 /var/run/sshd

#!/bin/sh

echo '#!/bin/sh

# Create glassfish public key
su - glassfish << EOF
ssh-keygen -b 2048 -t rsa -f .ssh/id_rsa -q -N ""
EOF' > /usr/lib/glassfish5/bin/setup-ssh.sh
chmod 0555 /usr/lib/glassfish5/bin/setup-ssh.sh

echo '[program:glassfish-ssh]
command=/usr/lib/glassfish5/bin/setup-ssh.sh
startsecs=0
autorestart=false' > \
/etc/supervisor/conf.d/glassfish-ssh.conf


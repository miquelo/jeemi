#!/bin/sh

apt-get -y install unzip

unzip /tmp/setup/tmp/glassfish-5.0.zip -d /usr/lib

apt-get -y remove unzip

useradd \
-d /usr/lib/glassfish5 \
-s /bin/bash \
glassfish
echo "
PATH=\$HOME/bin:\$PATH
export PATH
" > /usr/lib/glassfish5/.bash_profile
chown -R glassfish:glassfish /usr/lib/glassfish5

su - glassfish << EOF
# Deleting default domain
asadmin \
delete-domain \
domain1

mkdir .ssh
EOF

rm -rf /usr/lib/glassfish5/glassfish/domains/

sed -ie 's/="..\/domains"/="\/var\/glassfish\/domains"/g' \
/usr/lib/glassfish5/glassfish/config/asenv.conf
sed -ie 's/="..\/nodes"/="\/var\/glassfish\/nodes"/g' \
/usr/lib/glassfish5/glassfish/config/asenv.conf

mkdir -p /var/glassfish/domains
mkdir -p /var/glassfish/nodes
chown -R glassfish:glassfish /var/glassfish

echo '#!/bin/sh

ssh -oStrictHostKeyChecking=no $1@$2 "mkdir -p $3/$4/agent" 2> /dev/null
scp -oStrictHostKeyChecking=no \
$5/$6/config/master-password $1@$2:$3/$4/agent 2> /dev/null' > \
/usr/lib/glassfish5/bin/install-master-password.sh
chown glassfish:glassfish /usr/lib/glassfish5/bin/install-master-password.sh
chmod 0555 /usr/lib/glassfish5/bin/install-master-password.sh

echo '#!/bin/sh

KEYSTORE_TEMP_PATH=/tmp/keystore-$1
KEYSTORE_CACERTS_TEMP_PATH=$KEYSTORE_TEMP_PATH/cacerts
KEYSTORE_CERTS_TEMP_PATH=$KEYSTORE_TEMP_PATH/certs
KEYSTORE_KEYS_TEMP_PATH=$KEYSTORE_TEMP_PATH/keys
KEYSTORE_CERTKEYS_TEMP_PATH=$KEYSTORE_TEMP_PATH/certkeys

mkdir -p $KEYSTORE_CACERTS_TEMP_PATH
mkdir -p $KEYSTORE_CERTS_TEMP_PATH
mkdir -p $KEYSTORE_KEYS_TEMP_PATH
mkdir -p $KEYSTORE_CERTKEYS_TEMP_PATH' > \
/usr/lib/glassfish5/bin/keystore-update-begin.sh
chown glassfish:glassfish /usr/lib/glassfish5/bin/keystore-update-begin.sh
chmod 0555 /usr/lib/glassfish5/bin/keystore-update-begin.sh

echo '#!/bin/sh

KEYSTORE_TEMP_PATH=/tmp/keystore-$1
KEYSTORE_CACERTS_TEMP_PATH=$KEYSTORE_TEMP_PATH/cacerts
KEYSTORE_CERTS_TEMP_PATH=$KEYSTORE_TEMP_PATH/certs
KEYSTORE_KEYS_TEMP_PATH=$KEYSTORE_TEMP_PATH/keys
KEYSTORE_CERTKEYS_TEMP_PATH=$KEYSTORE_TEMP_PATH/certkeys
KEYSTORE_PATH=/var/glassfish/domains/$1/config/keystore.jks
CACERTS_PATH=/var/glassfish/domains/$1/config/cacerts.jks

if [ "$(ls -A $KEYSTORE_CACERTS_TEMP_PATH)" ]
then
	for f in $KEYSTORE_CACERTS_TEMP_PATH/*
	do
		KEYSTORE_ALIAS=$(basename $f)
		
		keytool \
		-delete \
		-alias $KEYSTORE_ALIAS \
		-keystore $CACERTS_PATH \
		-noprompt \
		-storepass $2 2> /dev/null
		
		keytool \
		-import \
		-alias $KEYSTORE_ALIAS \
		-file $f \
		-keystore $CACERTS_PATH \
		-noprompt \
		-storepass $2 2> /dev/null
	done
fi

if [ "$(ls -A $KEYSTORE_CERTS_TEMP_PATH)" ]
then
	for f in $KEYSTORE_CERTS_TEMP_PATH/*
	do
		KEYSTORE_ALIAS=$(basename $f)
		
		openssl pkcs12 \
		-export \
		-name $(basename $f) \
		-out $KEYSTORE_CERTKEYS_TEMP_PATH/$KEYSTORE_ALIAS \
		-inkey $KEYSTORE_KEYS_TEMP_PATH/$KEYSTORE_ALIAS \
		-in $f \
		-passout pass:$2
		
		keytool \
		-delete \
		-alias $KEYSTORE_ALIAS \
		-keystore $KESTORE_PATH \
		-noprompt \
		-storepass $2 2> /dev/null
		
		keytool \
		-importkeystore \
		-srckeystore $KEYSTORE_CERTKEYS_TEMP_PATH/$KEYSTORE_ALIAS \
		-srcstoretype PKCS12 \
		-srcalias $KEYSTORE_ALIAS \
		-srcstorepass $2 \
		-destkeystore $KEYSTORE_PATH \
		-deststoretype JKS \
		-destalias $KEYSTORE_ALIAS \
		-deststorepass $2 2> /dev/null
	done
fi

rm -r $KEYSTORE_TEMP_PATH' > \
/usr/lib/glassfish5/bin/keystore-update-end.sh
chown glassfish:glassfish /usr/lib/glassfish5/bin/keystore-update-end.sh
chmod 0555 /usr/lib/glassfish5/bin/keystore-update-end.sh


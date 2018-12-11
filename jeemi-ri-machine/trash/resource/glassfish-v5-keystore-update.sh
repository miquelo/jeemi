#!/bin/sh

KEYSTORE_TEMP_PATH=/tmp/keystore-$2
KEYSTORE_CACERTS_TEMP_PATH=$KEYSTORE_TEMP_PATH/cacerts
KEYSTORE_CERTS_TEMP_PATH=$KEYSTORE_TEMP_PATH/certs
KEYSTORE_KEYS_TEMP_PATH=$KEYSTORE_TEMP_PATH/keys
KEYSTORE_CERTKEYS_TEMP_PATH=$KEYSTORE_TEMP_PATH/certkeys
KEYSTORE_PATH=/var/glassfish/domains/$1/config/keystore.jks
CACERTS_PATH=/var/glassfish/domains/$1/config/cacerts.jks

case "$1" in

begin)
mkdir -p $KEYSTORE_CACERTS_TEMP_PATH
mkdir -p $KEYSTORE_CERTS_TEMP_PATH
mkdir -p $KEYSTORE_KEYS_TEMP_PATH
mkdir -p $KEYSTORE_CERTKEYS_TEMP_PATH
;;

end)
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
		-storepass $3 2> /dev/null
		
		keytool \
		-import \
		-alias $KEYSTORE_ALIAS \
		-file $f \
		-keystore $CACERTS_PATH \
		-noprompt \
		-storepass $3 2> /dev/null
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
		-passout pass:$3
		
		keytool \
		-delete \
		-alias $KEYSTORE_ALIAS \
		-keystore $KESTORE_PATH \
		-noprompt \
		-storepass $3 2> /dev/null
		
		keytool \
		-importkeystore \
		-srckeystore $KEYSTORE_CERTKEYS_TEMP_PATH/$KEYSTORE_ALIAS \
		-srcstoretype PKCS12 \
		-srcalias $KEYSTORE_ALIAS \
		-srcstorepass $3 \
		-destkeystore $KEYSTORE_PATH \
		-deststoretype JKS \
		-destalias $KEYSTORE_ALIAS \
		-deststorepass $3 2> /dev/null
	done
fi

rm -r $KEYSTORE_TEMP_PATH
;;

*)
echo "$0 (begin|end)"
;;

esac


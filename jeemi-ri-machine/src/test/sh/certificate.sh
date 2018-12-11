#!/bin/sh

# https://jamielinux.com/docs/openssl-certificate-authority/index.html

ORIGINAL_DIR=$PWD
cd src/test/sh/certificate

OUTPUT_DIR=$ORIGINAL_DIR/target/certificate

if [ ! -d $OUTPUT_DIR ]
then

	mkdir -p $OUTPUT_DIR

	ROOT_OUTPUT_DIR=$OUTPUT_DIR/root
	mkdir -p $ROOT_OUTPUT_DIR

	mkdir -p $ROOT_OUTPUT_DIR/certs
	mkdir -p $ROOT_OUTPUT_DIR/newcerts
	mkdir -p $ROOT_OUTPUT_DIR/private

	touch $ROOT_OUTPUT_DIR/index.txt
	touch $ROOT_OUTPUT_DIR/index.txt.attr
	echo 1000 > $ROOT_OUTPUT_DIR/private/serial

	openssl genrsa \
	-aes256 \
	-out $ROOT_OUTPUT_DIR/private/key.pem \
	-passout pass:12345678 \
	4096

	openssl req \
	-config root.cnf \
	-key $ROOT_OUTPUT_DIR/private/key.pem \
	-new \
	-x509 \
	-days 7300 \
	-sha256 \
	-extensions v3_ca \
	-out $ROOT_OUTPUT_DIR/certs/cert.pem \
	-passin pass:12345678 \
	-subj "/O=JEEMI Test/OU=Development/CN=JEEMI Test Root"

	CA_OUTPUT_DIR=$OUTPUT_DIR/ca
	mkdir -p $CA_OUTPUT_DIR

	mkdir -p $CA_OUTPUT_DIR/certs
	mkdir -p $CA_OUTPUT_DIR/csr
	mkdir -p $CA_OUTPUT_DIR/newcerts
	mkdir -p $CA_OUTPUT_DIR/private

	touch $CA_OUTPUT_DIR/index.txt
	touch $CA_OUTPUT_DIR/index.txt.attr
	echo 1000 > $CA_OUTPUT_DIR/private/serial

	openssl genrsa \
	-aes256 \
	-out $CA_OUTPUT_DIR/private/key.pem \
	-passout pass:12345678 \
	4096

	openssl req \
	-config ca.cnf \
	-key $CA_OUTPUT_DIR/private/key.pem \
	-new \
	-sha256 \
	-out $CA_OUTPUT_DIR/csr/csr.pem \
	-passin pass:12345678 \
	-subj "/O=JEEMI Test/OU=Development/CN=JEEMI Test CA"

	openssl ca \
	-config root.cnf \
	-extensions v3_intermediate_ca \
	-days 3650 \
	-notext \
	-md sha256 \
	-in $CA_OUTPUT_DIR/csr/csr.pem \
	-out $CA_OUTPUT_DIR/certs/cert.pem \
	-passin pass:12345678 \
	-batch

	cat \
	$CA_OUTPUT_DIR/certs/cert.pem \
	$ROOT_OUTPUT_DIR/certs/cert.pem \
	> $CA_OUTPUT_DIR/certs/chain.pem

	SERVER_OUTPUT_DIR=$OUTPUT_DIR/server
	mkdir -p $SERVER_OUTPUT_DIR

	mkdir -p $SERVER_OUTPUT_DIR/certs
	mkdir -p $SERVER_OUTPUT_DIR/csr
	mkdir -p $SERVER_OUTPUT_DIR/private

	openssl genrsa \
	-out $SERVER_OUTPUT_DIR/private/jeemi.test.key.pem \
	2048

	openssl req \
	-config ca.cnf \
	-key $SERVER_OUTPUT_DIR/private/jeemi.test.key.pem \
	-new \
	-sha256 \
	-out $SERVER_OUTPUT_DIR/csr/jeemi.test.csr.pem \
	-passin pass:12345678 \
	-subj "/O=JEEMI Test/OU=Development/CN=jeemi.test"

	openssl ca \
	-config ca.cnf \
	-extensions server_cert \
	-days 375 \
	-notext \
	-md \
	sha256 \
	-in $SERVER_OUTPUT_DIR/csr/jeemi.test.csr.pem \
	-out $SERVER_OUTPUT_DIR/certs/jeemi.test.cert.pem \
	-passin pass:12345678 \
	-batch
	
	CLASSES_DIR=$ORIGINAL_DIR/target/test-classes/net/jeemi/ri/test/certificate
	mkdir -p $CLASSES_DIR
	
	CLASSES_ROOT_DIR=$CLASSES_DIR/root
	mkdir $CLASSES_ROOT_DIR
	cp $ROOT_OUTPUT_DIR/certs/*.pem $CLASSES_ROOT_DIR
	cp $ROOT_OUTPUT_DIR/private/*.pem $CLASSES_ROOT_DIR
	
	CLASSES_CA_DIR=$CLASSES_DIR/ca
	mkdir $CLASSES_CA_DIR
	cp $CA_OUTPUT_DIR/certs/*.pem $CLASSES_CA_DIR
	cp $CA_OUTPUT_DIR/private/*.pem $CLASSES_CA_DIR
	
	CLASSES_SERVER_DIR=$CLASSES_DIR/server
	mkdir $CLASSES_SERVER_DIR
	cp $SERVER_OUTPUT_DIR/certs/* $CLASSES_SERVER_DIR
	cp $SERVER_OUTPUT_DIR/private/* $CLASSES_SERVER_DIR
	
fi

cd $ORIGINAL_DIR

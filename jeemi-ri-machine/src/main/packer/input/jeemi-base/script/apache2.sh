#!/bin/sh

apt-get -y install apache2 libapache2-mod-jk

echo '
ServerAdmin     webmaster@localhost
ServerName      localhost' >> \
/etc/apache2/apache2.conf

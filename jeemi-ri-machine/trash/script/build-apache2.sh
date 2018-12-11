#!/bin/sh

apt-get -y install apache2 libapache2-mod-jk

a2dissite 000-default
rm /etc/apache2/sites-available/000-default.conf
rm /etc/libapache2-mod-jk/workers.properties

mkdir /home/farmer/lib/apache2
chown farmer:farmer /home/farmer/lib/apache2

chmod -R go+rX /var/log/apache2

echo '
ServerAdmin     webmaster@localhost
ServerName      localhost' >> \
/etc/apache2/apache2.conf

SEARCH_PATTERN="JkWorkersFile \/etc\/libapache2-mod-jk\/workers.properties"
REPLACE_PATTERN="JkWorkersFile \/home\/farmer\/lib\/apache2\/workers.properties"
sed -ie "s/$SEARCH_PATTERN/$REPLACE_PATTERN/g" \
/etc/apache2/mods-enabled/jk.conf

a2enmod ssl
a2ensite glassfish
a2ensite glassfish-ssl

service apache2 stop

echo 'apache2' >> /root/lib/service.list


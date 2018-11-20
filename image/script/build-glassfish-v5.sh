#!/bin/sh

apt-get -y install unzip

unzip /tmp/build/glassfish-5.0.zip -d /usr/lib
rm /tmp/build/glassfish-5.0.zip

apt-get -y remove unzip

# FIX GlassFish 5.0 + JDK 8u181
apt-get -y install zip
zip --delete \
/usr/lib/glassfish5/glassfish/modules/endorsed/grizzly-npn-bootstrap.jar \
'sun/*'
apt-get -y remove zip

rm -rf /usr/lib/glassfish5/glassfish/domains/
rm -rf /usr/lib/glassfish5/glassfish/nodes/

sed -ie 's/="..\/domains"/="\/home\/farmer\/lib\/glassfish\/domains"/g' \
/usr/lib/glassfish5/glassfish/config/asenv.conf
sed -ie 's/="..\/nodes"/="\/home\/farmer\/lib\/glassfish\/nodes"/g' \
/usr/lib/glassfish5/glassfish/config/asenv.conf

mkdir /home/farmer/lib/glassfish
mkdir /home/farmer/lib/glassfish/domains
mkdir /home/farmer/lib/glassfish/nodes
chown -R farmer:farmer /home/farmer/lib/glassfish

echo 'PATH=$PATH:/usr/lib/glassfish5/bin' >> /home/farmer/.bash_profile

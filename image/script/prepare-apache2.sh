#!/bin/sh

apt-get -y install sudo

sed -ie 's/%sudo\tALL=(ALL:ALL) ALL/%sudo\tALL=(ALL:ALL) NOPASSWD:ALL/g' \
/etc/sudoers

apt-get -y install apache2 libapache2-mod-jk

mkdir /usr/apache2
useradd \
-d /usr/apache2 \
-s /bin/bash \
apache2
echo "
PATH=\$HOME/bin:\$PATH
export PATH
" > /usr/apache2/.bash_profile
addgroup apache2 sudo
chown -R apache2:apache2 /usr/apache2
chown -R apache2 /etc/apache2

a2dissite 000-default
rm /etc/apache2/sites-available/000-default.conf
rm /etc/libapache2-mod-jk/workers.properties

mkdir /usr/apache2/lib
chown apache2:apache2 /usr/apache2/lib

touch /usr/apache2/lib/workers.properties
chown apache2:apache2 /usr/apache2/lib/workers.properties

mkdir /usr/apache2/bin
chown apache2:apache2 /usr/apache2/bin

echo '
ServerAdmin     webmaster@localhost
ServerName      localhost' >> \
/etc/apache2/apache2.conf

echo '<VirtualHost *:80>

    DocumentRoot                /var/www/html
	
    JkLogLevel                  debug
    JkLogStampFormat            "[%a %b %d %H:%M:%S %Y] "
    JkOptions                   +ForwardKeySize +ForwardURICompat \\
                                -ForwardDirectories
    JkRequestLogFormat          "%w %V %T"
    JkMount                     /* glassfish
    
    ErrorLog                    ${APACHE_LOG_DIR}/error.log
    CustomLog                   ${APACHE_LOG_DIR}/access.log combined
    
</VirtualHost>' > \
/etc/apache2/sites-available/glassfish.conf

echo '<VirtualHost _default_:443>

    DocumentRoot                /var/www/html
    
    SSLEngine on
    SSLCipherSuite              ALL:!ADH:!EXP56:RC4+RSA:\\
+HIGH:+MEDIUM:+LOW:+SSLv2:+EXP:+eNULL
    SSLCertificateFile          /usr/apache2/lib/cert.pem
    SSLCertificateKeyFile       /usr/apache2/lib/key.pem
    # SSLCertificateChainFile   /etc/apache2/ssl.crt/server-ca.crt
    # SSLCACertificatePath      /etc/ssl/certs/
    # SSLCACertificateFile      /etc/apache2/ssl.crt/ca-bundle.crt
    # SSLCARevocationPath       /etc/apache2/ssl.crl/
    # SSLCARevocationFile       /etc/apache2/ssl.crl/ca-bundle.crl
    
    # SSLVerifyClient           require
    # SSLVerifyDepth            10
    # SSLOptions                +FakeBasicAuth +ExportCertData +StrictRequire
    # BrowserMatch              "MSIE [2-6]" \\
    #                           nokeepalive ssl-unclean-shutdown \\
    #                           downgrade-1.0 force-response-1.0
    
    JkLogLevel                  debug
    JkLogStampFormat            "[%a %b %d %H:%M:%S %Y] "
    JkOptions                   +ForwardKeySize +ForwardURICompat \\
                                -ForwardDirectories
    JkRequestLogFormat          "%w %V %T"
    JkMount                     /* glassfish
    
    # SSL between load balancer and GlassFish Server
    # JkExtractSSL              On
    # JkHTTPSIndicator          HTTPS
    # JkSESSIONIndicator        SSL_SESSION_ID
    # JkCIPHERIndicator         SSL_CIPHER
    # JkCERTSIndicator          SSL_CLIENT_CERT
    
    LogLevel                    info ssl:warn
    
    ErrorLog                    ${APACHE_LOG_DIR}/error.log
    CustomLog                   ${APACHE_LOG_DIR}/access.log combined
    
</VirtualHost>' > \
/etc/apache2/sites-available/glassfish-ssl.conf

SEARCH_PATTERN="JkWorkersFile \/etc\/libapache2-mod-jk\/workers.properties"
REPLACE_PATTERN="JkWorkersFile \/usr\/apache2\/lib\/workers.properties"
sed -ie "s/$SEARCH_PATTERN/$REPLACE_PATTERN/g" \
/etc/apache2/mods-enabled/jk.conf

a2enmod ssl
a2ensite glassfish
a2ensite glassfish-ssl

service apache2 stop

echo '#!/bin/bash

echo $APACHE2_CERTIFICATE | base64 \
--decode > /usr/apache2/lib/cert.pem

echo $APACHE2_PRIVATE_KEY | base64 \
--decode > /usr/apache2/lib/key.pem' > \
/root/bin/apache2-setup.sh
chmod 0555 /root/bin/apache2-setup.sh

echo '[program:apache2-setup]
command=/root/bin/apache2-setup.sh
startsecs=0
autorestart=false' > \
/etc/supervisor/conf.d/apache2-setup.conf

echo '[program:apache2]
command=/usr/sbin/apache2ctl -DFOREGROUND
autostart=false' > \
/etc/supervisor/conf.d/apache2.conf

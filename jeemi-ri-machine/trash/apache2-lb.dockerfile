#
# Apache 2 Load Balancer
#

FROM jeemi/debian-ssh:latest

COPY [ "script/build-apache2.sh", "/tmp/build.sh" ]
COPY [ "resource/lb-workers.sh", "/home/farmer/bin/lb-workers.sh" ]
COPY [ \
"resource/vh-glassfish.conf", \
"/etc/apache2/sites-available/glassfish.conf" \
]
COPY [ \
"resource/vh-glassfish-ssl.conf", \
"/etc/apache2/sites-available/glassfish-ssl.conf" \
]
RUN [ "sh", "/tmp/build.sh" ]

COPY [ "script/build-clean.sh", "/tmp/build.sh" ]
RUN [ "sh", "/tmp/build.sh" ]

RUN [ "rm", "/tmp/build.sh" ]

COPY [ "script/setup-apache2-lb.sh", "/tmp/setup/apache2-lb.sh" ]

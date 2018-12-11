#
# JEEMI Agent
#

FROM jeemi/debian-ssh:latest

COPY [ "script/build-apache2.sh", "/tmp/build.sh" ]
COPY [ \
"resource/vh-glassfish.conf", \
"/etc/apache2/sites-available/glassfish.conf" \
]
COPY [ \
"resource/vh-glassfish-ssl.conf", \
"/etc/apache2/sites-available/glassfish-ssl.conf" \
]
RUN [ "sh", "/tmp/build.sh" ]

COPY [ "script/build-jdk8.sh", "/tmp/build.sh" ]
RUN [ "sh", "/tmp/build.sh" ]

COPY [ "script/build-glassfish-v5.sh", "/tmp/build.sh" ]
COPY [ "package/glassfish-5.0.zip", "/tmp/build/glassfish-5.0.zip" ]
RUN [ "sh", "/tmp/build.sh" ]

COPY [ "script/build-clean.sh", "/tmp/build.sh" ]
RUN [ "sh", "/tmp/build.sh" ]

RUN [ "rm", "/tmp/build.sh" ]
RUN [ "rm", "-r", "/tmp/build" ]

COPY [ "script/setup-agent.sh", "/tmp/setup/agent.sh" ]


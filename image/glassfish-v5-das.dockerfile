#
# GlassFish Server V5 - DAS
#

FROM jeemi/debian-ssh:latest

COPY [ "script/build-jdk8.sh", "/tmp/build.sh" ]
RUN [ "sh", "/tmp/build.sh" ]

COPY [ "script/build-glassfish-v5.sh", "/tmp/build.sh" ]
COPY [ "package/glassfish-5.0.zip", "/tmp/build/glassfish-5.0.zip" ]
RUN [ "sh", "/tmp/build.sh" ]

COPY [ "script/build-clean.sh", "/tmp/build.sh" ]
RUN [ "sh", "/tmp/build.sh" ]

RUN [ "rm", "/tmp/build.sh" ]
RUN [ "rm", "-r", "/tmp/build" ]

COPY [ "script/setup-ssh-client.sh", "/tmp/setup/ssh-client.sh" ]
COPY [ "script/setup-glassfish-v5-das.sh", "/tmp/setup/glassfish-v5-das.sh" ]
COPY [ \
"resource/glassfish-v5-install-master-password.sh", \
"/home/farmer/bin/install-master-password.sh" \
]
COPY [ \
"resource/glassfish-v5-keystore-update.sh", \
"/home/farmer/bin/keystore-update.sh" \
]

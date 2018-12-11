#
# GlassFish Server V5 - Executor
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

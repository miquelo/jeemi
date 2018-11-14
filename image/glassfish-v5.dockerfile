#
# GlassFish Server V5
#

FROM jeemi/debian:latest

COPY [ "script/prepare-jdk8.sh", "/tmp/setup/execute.sh" ]
RUN [ "sh", "/tmp/setup/execute.sh" ]

COPY [ "script/prepare-glassfish-v5.sh", "/tmp/setup/execute.sh" ]
COPY [ "package/glassfish-5.0.zip", "/tmp/setup/tmp/glassfish-5.0.zip" ]
RUN [ "sh", "/tmp/setup/execute.sh" ]

COPY [ "script/clean-system.sh", "/tmp/setup/execute.sh" ]
RUN [ "sh", "/tmp/setup/execute.sh" ]

RUN [ "rm", "-r", "/tmp/setup" ]


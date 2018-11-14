#
# GlassFish Server V5 with SSH support
#

FROM jeemi/debian-ssh:latest

ENV SSH_USER=glassfish

COPY [ "script/prepare-jdk8.sh", "/tmp/setup/execute.sh" ]
RUN [ "sh", "/tmp/setup/execute.sh" ]

COPY [ "script/prepare-glassfish-v5.sh", "/tmp/setup/execute.sh" ]
COPY [ "package/glassfish-5.0.zip", "/tmp/setup/tmp/glassfish-5.0.zip" ]
RUN [ "sh", "/tmp/setup/execute.sh" ]

COPY [ "script/prepare-glassfish-v5-ssh.sh", "/tmp/setup/execute.sh" ]
RUN [ "sh", "/tmp/setup/execute.sh" ]

COPY [ "script/clean-system.sh", "/tmp/setup/execute.sh" ]
RUN [ "sh", "/tmp/setup/execute.sh" ]

RUN [ "rm", "-r", "/tmp/setup" ]


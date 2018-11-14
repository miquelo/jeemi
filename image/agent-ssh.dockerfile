#
# JEEMI Agent with SSH support
#

FROM jeemi/glassfish-v5-ssh:latest

COPY [ "script/prepare-apache2.sh", "/tmp/setup/execute.sh" ]
RUN [ "sh", "/tmp/setup/execute.sh" ]

COPY [ "script/prepare-agent.sh", "/tmp/setup/execute.sh" ]
RUN [ "sh", "/tmp/setup/execute.sh" ]

RUN [ "rm", "-r", "/tmp/setup" ]


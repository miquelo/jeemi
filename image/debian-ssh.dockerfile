#
# Debian Linux with Supervisor and SSH support
#

FROM jeemi/debian:latest

COPY [ "script/prepare-ssh-server.sh", "/tmp/setup/execute.sh" ]
RUN [ "sh", "/tmp/setup/execute.sh" ]

COPY [ "script/clean-system.sh", "/tmp/setup/execute.sh" ]
RUN [ "sh", "/tmp/setup/execute.sh" ]

RUN [ "rm", "-r", "/tmp/setup" ]


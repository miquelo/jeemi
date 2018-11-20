#
# Debian Linux with SSH support
#

FROM jeemi/debian:latest

COPY [ "script/build-ssh-server.sh", "/tmp/build.sh" ]
COPY [ "resource/authkeys.sh", "/usr/bin/authkeys" ]
RUN [ "sh", "/tmp/build.sh" ]

COPY [ "script/build-clean.sh", "/tmp/build.sh" ]
RUN [ "sh", "/tmp/build.sh" ]

RUN [ "rm", "/tmp/build.sh" ]


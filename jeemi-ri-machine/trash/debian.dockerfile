#
# Debian Linux
#

FROM library/debian:stretch-backports

COPY [ "script/build-prepare.sh", "/tmp/build.sh" ]
RUN [ "sh", "/tmp/build.sh" ]

COPY [ "script/build-clean.sh", "/tmp/build.sh" ]
RUN [ "sh", "/tmp/build.sh" ]

RUN [ "rm", "/tmp/build.sh" ]

ENTRYPOINT [ "/init" ]


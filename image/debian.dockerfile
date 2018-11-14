#
# Debian Linux with Supervisor
#

FROM library/debian:stretch-backports

COPY [ "script/prepare-system.sh", "/tmp/setup/execute.sh" ]
RUN [ "sh", "/tmp/setup/execute.sh" ]

COPY [ "script/prepare-supervisor.sh", "/tmp/setup/execute.sh" ]
RUN [ "sh", "/tmp/setup/execute.sh" ]

COPY [ "script/clean-system.sh", "/tmp/setup/execute.sh" ]
RUN [ "sh", "/tmp/setup/execute.sh" ]

RUN [ "rm", "-r", "/tmp/setup" ]
ENTRYPOINT [ "supervisord", "-n" ]


#!/bin/sh

echo 'Enabling SSH farmer client authentication.'

mkdir -p /home/farmer/.ssh
ssh-keygen -b 2048 -t rsa -f /home/farmer/.ssh/id_rsa -q -N ""
chown -R farmer:farmer /home/farmer/.ssh

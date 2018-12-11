#!/bin/sh

ssh -oStrictHostKeyChecking=no farmer@$1 "mkdir -p $2/$3/agent" 2> /dev/null
scp -oStrictHostKeyChecking=no \
$4/$5/config/master-password farmer@$1:$2/$3/agent 2> /dev/null


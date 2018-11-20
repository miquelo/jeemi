#!/bin/sh

DIR="~${1}/.ssh/authorized_keys.d/*"
cat `eval echo $DIR`

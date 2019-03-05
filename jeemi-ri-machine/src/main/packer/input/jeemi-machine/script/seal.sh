#!/bin/sh

# Enforce SSH security

SEARCH_PATTERN="StrictModes no"
REPLACE_PATTERN="StrictModes yes"
sed -ie "s/$SEARCH_PATTERN/$REPLACE_PATTERN/g" \
/etc/ssh/sshd_config

SEARCH_PATTERN="PermitRootLogin yes"
REPLACE_PATTERN="PermitRootLogin no"
sed -ie "s/$SEARCH_PATTERN/$REPLACE_PATTERN/g" \
/etc/ssh/sshd_config

SEARCH_PATTERN="PasswordAuthentication yes"
REPLACE_PATTERN="PasswordAuthentication no"
sed -ie "s/$SEARCH_PATTERN/$REPLACE_PATTERN/g" \
/etc/ssh/sshd_config

# Disable SUDO

apt-get remove -y sudo
rm -r /etc/sudoers*

# Disable root account

# SEARCH_PATTERN="root:x:0:0:root:\/root:\/bin\/bash"
# REPLACE_PATTERN="root:x:0:0:root:\/root:\/sbin\/nologin"
# sed -ie "s/$SEARCH_PATTERN/$REPLACE_PATTERN/g" \
# /etc/passwd

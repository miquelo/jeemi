#!/bin/sh

# Disabling CDROM entries to avoid prompts to insert a disk

sed -i "/^deb cdrom:/s/^/#/" /etc/apt/sources.list

# Setup SSH for root account

echo 'root:root' | chpasswd

echo 'StrictModes no' >> /etc/ssh/sshd_config
echo 'PermitRootLogin yes' >> /etc/ssh/sshd_config
echo 'PasswordAuthentication yes' >> /etc/ssh/sshd_config

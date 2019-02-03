#!/bin/sh

apt-get -y autoremove

userdel packer
rm -r /home/packer

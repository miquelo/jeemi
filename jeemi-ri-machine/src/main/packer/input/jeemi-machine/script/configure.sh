#!/bin/sh

cp -R /tmp/jeemi/packer/* /
rm -r /tmp/jeemi/packer

chmod 500 /usr/local/bin/jeemi-lifecycle-ctl

systemctl daemon-reload
systemctl enable jeemi-lifecycle

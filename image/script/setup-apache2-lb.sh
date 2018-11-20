#!/bin/sh

echo 'Clearing workers.'

chown farmer:farmer /home/farmer/bin/lb-workers.sh

su - farmer << EOF
lb-workers.sh clear
EOF

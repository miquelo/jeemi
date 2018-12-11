#!/bin/sh

echo 'Giving GlassFish DAS utilities ownership to farmer.'

chown farmer:farmer /home/farmer/bin/glassfish-*.sh

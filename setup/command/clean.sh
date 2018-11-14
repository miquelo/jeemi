#!/bin/bash

mvn -f module/jeemi/pom.xml clean install
mvn -f module/jeemi-dist/pom.xml clean

setup/shared/remove-images.sh
setup/shared/remove-certificates.sh

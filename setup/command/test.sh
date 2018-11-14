#!/bin/bash

mvn -f module/jeemi/pom.xml install
mvn -f module/jeemi-dist/pom.xml verify site

setup/shared/build-images.sh


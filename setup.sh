#!/bin/bash

case "$1" in

build)
mvn -e -f specs/modules/jeemi-api/pom.xml clean install site && \
mvn -e -f geoffrey/modules/geoffrey-project/pom.xml clean install && \
mvn -e -f geoffrey/modules/geoffrey-assistant-local/pom.xml clean install && \
mvn -e -f geoffrey/modules/geoffrey-assistant-google/pom.xml clean install
;;

help)
echo "\n"
build              Do packaging on all modules, install them locally and
                   generate the specifications site
;;

*)
echo "Unknown command '$1'. Use '$0 help' to see available commands"
;;

esac


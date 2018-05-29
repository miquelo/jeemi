#!/bin/bash

case "$1" in

build)
mvn -e -f specs/modules/jeemi-api/pom.xml clean install site && \
mvn -e -f jeremias/modules/jeremias-project/pom.xml clean install && \
mvn -e -f jeremias/modules/jeremias-agent/pom.xml clean install && \
mvn -e -f jeremias/modules/jeremias-management/pom.xml clean install && \
mvn -e -f jeremias/modules/jeremias-agent-gf4-asadmin/pom.xml clean install && \
mvn -e -f jeremias/modules/jeremias-agent-gf4-asadmin-adapter/pom.xml clean install && \
mvn -e -f jeremias/modules/jeremias-agent-gf4-bean/pom.xml clean install
;;

help)
echo "\n"
build  Do packaging on all modules, install them locally and generate the
       specifications site
;;

*)
echo "Unknown command '$1'. Use '$0 help' to see available commands"
;;

esac

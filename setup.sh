#!/bin/bash

case "$1" in

build)
mvn -f specs/modules/jeemi-api/pom.xml clean install site && \
mvn -f geoffrey/modules/geoffrey-project/pom.xml clean install && \
mvn -f geoffrey/modules/geoffrey-gui-wizard/pom.xml clean install && \
mvn -f geoffrey/modules/geoffrey-assistant-local/pom.xml clean install && \
mvn -f geoffrey/modules/geoffrey-assistant-google/pom.xml clean install && \
mvn -f geoffrey/modules/geoffrey-gui-wizard-local/pom.xml clean install && \
mvn -f geoffrey/modules/geoffrey-gui-wizard-google/pom.xml clean install && \
mvn -f geoffrey/modules/geoffrey-gui/pom.xml clean install
;;

test-geoffrey-gui)
sh $0 build

NATIVE_DIR=geoffrey/modules/geoffrey-gui/target/jfx/native
PACKAGE_NAME=jeemi-assistants
PACKAGE_PATH_DEB=$NATIVE_DIR/$PACKAGE_NAME-1.0.deb

if [ `command -v dpkg` ] && [ -f $PACKAGE_PATH_DEB ]; then
su -c "dpkg --install $PACKAGE_PATH_DEB"
else
echo "Package should be installed manually"
fi

read -r -p 'You can do manual testing... [Press some key when finished]' _

if [ `command -v dpkg` ]; then
su -c "dpkg --remove $PACKAGE_NAME"
else
echo "You should remove package manually"
fi
;;

help)
echo "\
build              Do packaging on all modules, install them locally and
                   generate the specifications site
test-geoffrey-gui  Install a Geoffrey agent manager graphical user interface for
                   manual testing purposes"
;;

*)
echo "Unknown command '$1'. Use '$0 help' to see available commands"
;;

esac


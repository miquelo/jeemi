#!/bin/bash

mvn -f modules/jeemi/pom.xml install
mvn -P tool-installer -f modules/jeemi-dist/pom.xml package
cp -R modules/jeemi-ri-tool/target/dist/* $HOME
chmod +x $HOME/bin/jeemi-tool
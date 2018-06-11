#!/bin/bash

mvn -f modules/jeemi/pom.xml install
mvn -f modules/jeemi-dist/pom.xml verify site

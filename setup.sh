#!/bin/bash

case "$1" in

clean)
./setup/command/clean.sh
;;

test)
./setup/command/test.sh
;;

deploy)
./setup/command/deploy.sh
;;

prove)
./setup/command/prove.sh
;;

help)
echo "Available commands
------------------
clean   Clean all generated contents.
test    Test the current snapshot.
deploy  Deploy a new version from the current snapshot.
prove   Prove the current snapshot for manual testing or demonstration."
;;

*)
echo "Unknown command '$1'. Use '$0 help' to see available commands"
;;

esac


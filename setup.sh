#!/bin/bash

case "$1" in

clean)
./setup/command/clean.sh
;;

test)
./setup/command/test.sh
;;

install-tool)
./setup/command/install-tool.sh
;;

help)
echo "Available commands
------------------
clean         Clean all generated files.
test          Test the current snaphot.
install-tool  Install RI tool from current source files."
;;

*)
echo "Unknown command '$1'. Use '$0 help' to see available commands"
;;

esac


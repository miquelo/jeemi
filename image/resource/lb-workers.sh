#!/bin/bash

# $1 - Command (clear|put|remove)
# $2 - Node name
# $3 - Host (Command PUT)
# $4 - Port (Command PUT)

# Apache2 reload is not required (See JkMountFileReload)

WORKERS_TEMP_DIR_PATH=/tmp/mod_jk_workers
WORKERS_FILE_PATH=/home/farmer/lib/apache2/workers.properties
NODE_PREFIX="node-"

rm -rf $WORKERS_TEMP_DIR_PATH
mkdir $WORKERS_TEMP_DIR_PATH

if [ -f $WORKERS_FILE_PATH ]
then
    while read WORKERS_FILE_LINE
    do
        if [[ $WORKERS_FILE_LINE =~ ^(.+\.$NODE_PREFIX)(.+)(\..+)$ ]]
        then
            printf "${BASH_REMATCH[0]}\n" >> \
            $WORKERS_TEMP_DIR_PATH/${BASH_REMATCH[2]}
        fi
    done < $WORKERS_FILE_PATH
fi

NODE_NAME="$2"
WORKER_FILE_PATH=$WORKERS_TEMP_DIR_PATH/$NODE_NAME

case "$1" in

clear)
rm -f $WORKERS_TEMP_DIR_PATH/*
;;

put)
WORKER_NAME="$NODE_PREFIX$NODE_NAME"
printf "worker.$WORKER_NAME.type=ajp13\n" > $WORKER_FILE_PATH
printf "worker.$WORKER_NAME.host=$3\n" >> $WORKER_FILE_PATH
printf "worker.$WORKER_NAME.port=$4\n" >> $WORKER_FILE_PATH
printf "worker.$WORKER_NAME.lbfactor=1\n" >> $WORKER_FILE_PATH
printf "worker.$WORKER_NAME.socket_keepalive=1\n" >> $WORKER_FILE_PATH
printf "worker.$WORKER_NAME.socket_timeout=300\n" >> $WORKER_FILE_PATH
;;

remove)
rm -f $WORKER_FILE_PATH
;;

*)
echo "$0 (clear|put|remove)"
;;

esac

printf '# Load balancer workers\n\n' > $WORKERS_FILE_PATH

printf 'worker.list=' >> $WORKERS_FILE_PATH

if [ -n "$(ls -A $WORKERS_TEMP_DIR_PATH/* 2> /dev/null)" ]
then
    for WORKER_FILE_PATH in $WORKERS_TEMP_DIR_PATH/*
    do
        NODE_NAME=$(basename -- "$WORKER_FILE_PATH")
        WORKER_NAME="$NODE_PREFIX$NODE_NAME"
        printf "$WORKER_NAME," >> $WORKERS_FILE_PATH
    done
fi

printf 'jk-status,jk-manager\n\n' >> $WORKERS_FILE_PATH

if [ -n "$(ls -A $WORKERS_TEMP_DIR_PATH/* 2> /dev/null)" ]
then
    for WORKER_FILE_PATH in $WORKERS_TEMP_DIR_PATH/*
    do
        cat $WORKER_FILE_PATH >> $WORKERS_FILE_PATH
        printf '\n' >> $WORKERS_FILE_PATH
    done
	
    printf 'worker.glassfish.type=lb\n' >> $WORKERS_FILE_PATH
    printf 'worker.glassfish.balance_workers=' >> $WORKERS_FILE_PATH

    SEPARATOR=''
    for WORKER_FILE_PATH in $WORKERS_TEMP_DIR_PATH/*
    do
        NODE_NAME=$(basename -- "$WORKER_FILE_PATH")
        WORKER_NAME="$NODE_PREFIX$NODE_NAME"
        printf "$WORKER_NAME$SEPARATOR" >> $WORKERS_FILE_PATH
        SEPARATOR=','
    done

    printf '\n' >> $WORKERS_FILE_PATH

    printf 'worker.glassfish.sticky_session=true\n\n' >> $WORKERS_FILE_PATH
fi

printf 'worker.jk-status.type=status\n' >> $WORKERS_FILE_PATH
printf 'worker.jk-manager.type=status\n' >> $WORKERS_FILE_PATH

rm -rf $WORKERS_TEMP_DIR_PATH

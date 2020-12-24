#!/usr/bin/env bash
if [[ $# -eq 0 ]] ; then
    echo 'You should specify container name!'
    exit 1
fi


docker cp ../target/homework2-1.0-SNAPSHOT-jar-with-dependencies.jar $1:/tmp
docker cp genarateInputData.sh $1:/

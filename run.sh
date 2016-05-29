#!/usr/bin/env bash

#
# command line runner for the witty servers
#

function cleanup() {
    kill ${CLIENT_PID} ${SERVER_PID}
}

trap cleanup EXIT

mvn clean package
java -jar consumer/target/consumer-1.0.0.jar &
SERVER_PID=$!

while ! nc localhost 8080 > /dev/null 2>&1 < /dev/null; do
    echo "$(date) - waiting for server at localhost:8080..."
    sleep 1
done

java -jar producer/target/producer-1.0.0.jar &
CLIENT_PID=$!

sleep 5
echo ""
echo "See logs in target/logs/producer.runtime.log"
echo "For EXIT press 'q'"

while :
do
    read -t 1 -n 1 key
    if [[ $key = q ]]
    then
        break
    fi
done

cleanup

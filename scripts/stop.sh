#!/bin/bash

echo "Stopping application..."

cd ../infrastructure || exit 1
docker-compose down

cd .. || exit 1

if [ -f "application.pid" ]; then
    PID=$(cat application.pid)
    if ps -p "$PID" > /dev/null; then
        echo "Stopping Java process (PID: $PID)..."
        kill "$PID"
        rm application.pid
    else
        echo "Process with PID $PID not found"
        rm application.pid
    fi
fi

echo "Application stopped"
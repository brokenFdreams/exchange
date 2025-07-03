# Exchange Application

## Architecture
I've used a Clean Architecture 

It's hard to start with, but easy to go with.

For an example: You can start with an in-memory repository and create the whole app with it, and then simply switch to actual database realization.

In my case, I used an in-memory exchange rate provider.

Core business logic in a "Domain" layer. This layer can exist even if application doesn't exist.

There you can find Aggregate "Account" and "TransferService" for a fund transfer between accounts

## Scripts

All scripts are in the scripts folder.

### Start
"./start-jar.sh" to start with a jar file

"./start-docker.sh" to start with a docker image

### Stop
"./stop.sh" to stop containers and to kill java process

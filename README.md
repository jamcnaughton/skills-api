# Skills - API

## About the Skills API

This repository hosts the spring web project which provide the Skills API and the liquibase project needed to set up the
database it utilises.

## Prerequisites

Before running the Skills API you will need at least the following:

* Java ([jdk-17.0.5](https://download.oracle.com/java/17/archive/jdk-17.0.5_windows-x64_bin.zip))
* Maven
* Docker Desktop

## Setup database

1. Open a terminal in this folder.
2. Run this command to create the docker container for the database: `docker-compose up --detach`
2. Run this command to populate the database: `mvn liquibase:update`

## Run database

1. Open Docker Desktop and start the `skills-db` container.
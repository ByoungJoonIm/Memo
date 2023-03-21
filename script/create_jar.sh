#!/bin/sh

cd ..
./gradlew bootJar
cp modules/server/build/libs/server-0.0.1-SNAPSHOT.jar jar

#!/bin/sh

java -jar /wine.jar db migrate winedb.yaml
java -jar /wine.jar server winedb.yaml

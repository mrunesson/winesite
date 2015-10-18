The main purpose of this project is to play with new technologies. Technologies
used are:

* docker including docker-machine, docker-compose, and maven integration
* dropwizard.io
* micro services

The long term idea is that it might turn out to be a wine database.


Build and run
=============

You need to have docker set up. If you run on OS X or Windows make sure you have
docker-machine set up.

Build and run integration tests for winesite:

    mvn clean verify

Build the docker environment use docker-compose:

    docker-compose build

To start:

    docker-compose start


Services
========

All are not yet created.

* wine - Representations of all wines
* grape - Handle all grapes
* cellar - Handle different cellars (support for sub-cellars)
* user - Users
* tasting - Handle tastings


Resources
=========

* /wine/winery/wine/vintage
* /grape/grapename
* /user/username
* /cellar/cellarid
* /tasting/tasteid/...

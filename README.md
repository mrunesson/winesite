


Build and run
=============

You need to have docker set up. If you run on OS X or Windows make sure you have run:

    $(boot2docker shellinit)

You do also want to forward the ports from the virtual box to the host machine:

    VBoxManage modifyvm "boot2docker-vm" --natpf1 "tcp-port5432,tcp,,5432,,5432";
    VBoxManage modifyvm "boot2docker-vm" --natpf1 "tcp-port8080,tcp,,8080,,8080";
    VBoxManage modifyvm "boot2docker-vm" --natpf1 "tcp-port8081,tcp,,8081,,8081";


Build a docker image for winedb with maven:

    mvn clean package

Start a postgres database for winedb:

    docker run --name winedb-postgres -e POSTGRES_PASSWORD=mysecretpassword postgres

Start winedb:

    docker run --link winedb-postgres:winedb-postgres -p 8080:8080 -p 8081:8081 winedb

You will be able to access port 8080 and 8081.

You have to create needed tables in postgres. Find out container id for your winedb container
with ``docker ps`` and then run:

    docker exec containerid java -jar winedb-1.0-SNAPSHOT.jar db migrate winedb.yml

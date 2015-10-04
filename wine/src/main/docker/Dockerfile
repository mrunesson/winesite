FROM java:8
MAINTAINER Magnus Runesson <magru@linuxalert.org>

EXPOSE 8080:8080 8081:8081

ENTRYPOINT ["java", "-jar", "/wine.jar", "server", "winedb.yaml"]

COPY winedb.yaml /
COPY wine.jar /

FROM java:8
MAINTAINER Magnus Runesson <magru@linuxalert.org>

EXPOSE 8080:8080 8081:8081

ENTRYPOINT ["/run.sh"]

COPY winedb.yaml /
COPY wine.jar /
COPY run.sh /
RUN chmod +x /run.sh

FROM java:8u111-jre-alpine
VOLUME /tmp
ADD authorization-server-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

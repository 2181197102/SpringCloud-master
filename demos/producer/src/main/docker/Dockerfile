FROM java:8u111-jre-alpine
VOLUME /tmp
ADD producer-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

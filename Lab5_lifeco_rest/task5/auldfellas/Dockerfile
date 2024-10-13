#FROM openjdk:8-jre-alpine
FROM adoptopenjdk:11-jre-hotspot
#ENV HOST=localhost
COPY target/auldfellas-0.0.1.jar /auldfellas.jar
#CMD ["/usr/bin/java", "-cp", "/auldfellas.jar", "service.auldfellas.Main"]
CMD java -jar /auldfellas.jar
#CMD sleep 10 && java -cp /auldfellas.jar $HOST
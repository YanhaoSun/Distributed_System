#FROM openjdk:8-jre-alpine
FROM adoptopenjdk:11-jre-hotspot
#ENV HOST=localhost
COPY target/dodgygeezers-0.0.1.jar /dodgygeezers.jar
#CMD ["/usr/bin/java", "-cp", "/dodgygeezers.jar", "service.dodgygeezers.Main"]
#CMD java -cp /dodgygeezers.jar $HOST
CMD java -jar /dodgygeezers.jar
#FROM openjdk:8-jre-alpine
FROM adoptopenjdk:11-jre-hotspot
#ENV HOST=localhost
COPY target/broker-0.0.1.jar /broker.jar
CMD sleep 10 && java -jar /broker.jar

#FROM openjdk:8-jre-alpine
#COPY target/broker-0.0.1-allinone.jar /broker.jar
#CMD ["/usr/bin/java", "-cp", "/broker.jar", "service.broker.Main"]
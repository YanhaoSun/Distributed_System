FROM openjdk:8-jre-alpine
ENV HOST=broker
COPY target/client-0.0.1-allinone.jar /client-0.0.1-allinone.jar
CMD java -jar /client-0.0.1-allinone.jar $HOST

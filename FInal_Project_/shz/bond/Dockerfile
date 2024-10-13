FROM openjdk:11-jre-slim

ENV HOST=localhost
COPY target/bond-0.0.1-allinone.jar /bond.jar 
CMD java -jar /bond.jar $HOST
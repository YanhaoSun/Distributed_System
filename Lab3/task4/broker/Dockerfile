FROM openjdk:8-jre-alpine
COPY target/broker-0.0.1-allinone.jar /broker.jar
CMD ["/usr/bin/java", "-cp", "/broker.jar", "service.broker.Main"]
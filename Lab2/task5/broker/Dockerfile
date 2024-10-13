FROM openjdk:8-jre-alpine
COPY target/broker-0.0.1-allinone.jar /broker-0.0.1-allinone.jar
CMD ["/usr/bin/java", "-cp", "/broker-0.0.1-allinone.jar", "service.broker.Main"]
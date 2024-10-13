FROM openjdk:8-jre-alpine
ENV HOST=localhost
COPY target/dodgygeezers-0.0.1-allinone.jar /dodgygeezers.jar
#CMD ["/usr/bin/java", "-cp", "/dodgygeezers.jar", "service.dodgygeezers.Main"]
#CMD java -cp /dodgygeezers.jar $HOST
CMD java -jar /dodgygeezers.jar $HOST
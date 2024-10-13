FROM openjdk:8-jre-alpine
ENV HOST=localhost
COPY target/auldfellas-0.0.1-allinone.jar /auldfellas.jar
#CMD ["/usr/bin/java", "-cp", "/auldfellas.jar", "service.auldfellas.Main"]
CMD java -jar /auldfellas.jar $HOST
#CMD sleep 10 && java -cp /auldfellas.jar $HOST
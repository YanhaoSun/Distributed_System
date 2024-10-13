#FROM openjdk:8-jre-alpine
FROM adoptopenjdk:11-jre-hotspot
#ENV HOST=localhost
COPY target/girlsallowed-0.0.1.jar /girlsallowed.jar
#CMD ["/usr/bin/java", "-cp", "/girlsallowed.jar", "service.girlsallowed.Main"]

#CMD sleep 10 && java -cp /girlsallowed.jar $HOST
CMD java -jar /girlsallowed.jar
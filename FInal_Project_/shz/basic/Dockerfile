FROM openjdk:11-jre-slim
EXPOSE 8081
COPY target/basic-0.0.1.jar /basic.jar
ENV DB_HOST=mysql_basic
ENV DB_PORT=3306
ENV DB_USER=root
ENV DB_PASSWORD=123
ENV BROKER_URL=http://broker:8082
CMD java -jar /basic.jar
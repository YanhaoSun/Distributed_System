FROM openjdk:11-jre-slim
EXPOSE 8080
COPY target/login-0.0.1.jar /login.jar
ENV DB_HOST=mysql_basic
ENV DB_PORT=3306
ENV DB_USER=root
ENV DB_PASSWORD=123
CMD java -jar /login.jar
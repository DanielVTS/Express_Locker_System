FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY src/main/crdb_cert/*.crt crdb.cert
ENTRYPOINT ["java","-jar","/app.jar"]
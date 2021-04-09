FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
ARG CRDB_CERT=src/main/crdb_cert/*.crt
COPY ${JAR_FILE} app.jar
COPY ${CRDB_CERT} crdb.crt
ENTRYPOINT ["java","-jar","/app.jar"]
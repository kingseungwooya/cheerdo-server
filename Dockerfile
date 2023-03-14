FROM openjdk:17-oracle

EXPOSE 443

ARG JAR_FILE=/build/libs/cheerdo-server-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
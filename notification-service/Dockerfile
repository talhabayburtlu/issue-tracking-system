#
# Build stage
#
FROM maven:3.9.8-eclipse-temurin-21 AS build
COPY ./notification-service/src /notification-service/src/
COPY ./notification-service/pom.xml /notification-service/
COPY ./pom.xml /
RUN mvn -f /notification-service/pom.xml clean package

#
# Package stage
#
FROM maven:3.9.8-eclipse-temurin-21
COPY --from=build /notification-service/target/notification-service-0.0.1-SNAPSHOT.jar /usr/local/lib/notification-service.jar
EXPOSE 9009
ENTRYPOINT ["java","-jar","/usr/local/lib/notification-service.jar"]
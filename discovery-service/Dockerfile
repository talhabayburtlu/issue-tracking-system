#
# Build stage
#
FROM maven:3.9.8-eclipse-temurin-21 AS build
COPY ./discovery-service/src /discovery-service/src/
COPY ./discovery-service/pom.xml /discovery-service/
COPY ./pom.xml /
RUN mvn -f /discovery-service/pom.xml clean package

#
# Package stage
#
FROM maven:3.9.8-eclipse-temurin-21
COPY --from=build /discovery-service/target/discovery-service-0.0.1-SNAPSHOT.jar /usr/local/lib/discovery-service.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/discovery-service.jar"]
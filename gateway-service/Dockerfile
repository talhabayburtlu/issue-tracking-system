#
# Build stage
#
FROM maven:3.9.8-eclipse-temurin-21 AS build
COPY ./gateway-service/src /gateway-service/src/
COPY ./gateway-service/pom.xml /gateway-service/
COPY ./pom.xml /
RUN mvn -f /gateway-service/pom.xml clean package

#
# Package stage
#
FROM maven:3.9.8-eclipse-temurin-21
COPY --from=build /gateway-service/target/gateway-service-0.0.1-SNAPSHOT.jar /usr/local/lib/gateway-service.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/usr/local/lib/gateway-service.jar"]
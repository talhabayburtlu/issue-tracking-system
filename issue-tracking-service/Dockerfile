#
# Build stage
#
FROM maven:3.9.8-eclipse-temurin-21 AS build
COPY ./issue-tracking-service/src /issue-tracking-service/src/
COPY ./issue-tracking-service/pom.xml /issue-tracking-service/
COPY ./pom.xml /
RUN mvn -f /issue-tracking-service/pom.xml clean package

#
# Package stage
#
FROM maven:3.9.8-eclipse-temurin-21
COPY --from=build /issue-tracking-service/target/issue-tracking-service-0.0.1-SNAPSHOT.jar /usr/local/lib/issue-tracking-service.jar
EXPOSE 9001
ENTRYPOINT ["java","-jar","/usr/local/lib/issue-tracking-service.jar"]
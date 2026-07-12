# Build Stage: Compile and package the Java Spring Boot application using Gradle 8
FROM gradle:8-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon -x test

# Runtime Stage: Run the compiled JAR in an Eclipse Temurin JRE 17 container
FROM eclipse-temurin:17-jre
WORKDIR /app
EXPOSE 7474
COPY --from=build /home/gradle/src/build/libs/offerOps-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
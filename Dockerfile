# Build stage
FROM maven:3.9.5-amazoncorretto-17 AS build
WORKDIR /app
COPY src /app/src
COPY pom.xml /app
RUN mvn clean package

# Run stage
FROM amazoncorretto:17
COPY --from=build /app/target/KameleoonTrialTask-0.0.1.jar /app/KameleoonTrialTask-0.0.1.jar
WORKDIR /app
CMD ["java", "-jar", "KameleoonTrialTask-0.0.1.jar"]








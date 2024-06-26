FROM maven:3.8.5-openjdk-18 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:18-ea-8-jdk-slim
COPY --from=build /target/HBV1-0.0.1-SNAPSHOT.jar HBV1.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","HBV1.jar"]
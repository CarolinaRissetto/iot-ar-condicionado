# Stage 1: Build the project
FROM maven:3.9.2-amazoncorretto-17 as build
LABEL authors="carolina"

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package

# Stage 2: Run the application
FROM amazoncorretto:17-alpine
LABEL authors="carolina"

WORKDIR /app
COPY --from=build /app/target/automacaoArCondicionado-0.0.1-SNAPSHOT.jar ./automacaoArCondicionado-0.0.1-SNAPSHOT.jar

EXPOSE 8080
CMD java -jar automacaoArCondicionado-0.0.1-SNAPSHOT.jar
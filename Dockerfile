FROM maven:3.9.0-eclipse-temurin-17-alpine AS build
COPY . .
RUN mvn clean package -DskipTests


FROM openjdk:19
COPY --from=build /target/Project-0.0.1-SNAPSHOT.jar /usr/share/app.jar
EXPOSE 8080
ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/app.jar"]
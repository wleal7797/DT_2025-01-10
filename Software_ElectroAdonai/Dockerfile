FROM openjdk:17-jdk-slim
LABEL authors="lealw"
ENTRYPOINT ["java", "-jar", "app.jar"]
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8080
RUN ./mvnw dependency:go-offline
COPY src ./src
CMD ["./mvnw", "spring-boot:run"]
COPY mvnw pom.xml ./
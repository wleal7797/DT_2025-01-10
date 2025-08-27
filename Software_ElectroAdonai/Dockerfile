# Etapa 1: compilar con Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar el descriptor y el código fuente
COPY pom.xml .
COPY src ./src

# Construir el JAR (sin ejecutar tests)
RUN mvn clean package -DskipTests

# Etapa 2: imagen final con solo el JAR
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copiar el .jar desde la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto de la aplicación
EXPOSE 8080

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]

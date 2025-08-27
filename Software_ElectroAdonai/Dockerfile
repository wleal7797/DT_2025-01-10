# Etapa 1: Build (Compilación)
FROM maven:3.9.6-openjdk-17-slim AS build
WORKDIR /app

# Copia los archivos de configuración de Maven primero (para cache de dependencias)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia el código fuente y compila
COPY src ./src
RUN mvn clean package -DskipTests -B

# Etapa 2: Runtime (Ejecución)
FROM openjdk:17-jdk-slim
LABEL maintainer="leal.wilson19@gmail.com"

# Crea usuario no-root
RUN addgroup --system spring && adduser --system spring --ingroup spring

# Establece directorio de trabajo
WORKDIR /app

# Copia el JAR desde la etapa de build
COPY --from=build /app/target/Prueba1-0.0.1-SNAPSHOT.jar app.jar

# Cambia permisos
RUN chown spring:spring app.jar

# Cambia a usuario no-root
USER spring

# Expone puerto
EXPOSE 8080

# Variables de entorno
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Configuración para Render - usa la variable PORT si está disponible
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dserver.port=${PORT:-8080} -jar app.jar"]

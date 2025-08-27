FROM openjdk:17-jdk-slim
LABEL maintainer="leal.wilson19@gmail.com"
RUN addgroup --system spring && adduser --system spring --ingroup spring
WORKDIR /app
COPY target/Prueba1-0.0.1-SNAPSHOT.jar app.jar
RUN chown spring:spring app.jar
USER spring
EXPOSE 8080
ENV JAVA_OPTS="-Xmx512m -Xms256m"
# Configuración para Render - usa la variable PORT si está disponible
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dserver.port=${PORT:-8080} -jar app.jar"]
FROM openjdk:17-jdk-slim
LABEL maintainer="leal.wilson19@gmail.com"
RUN addgroup --system spring && adduser --system spring --ingroup spring
WORKDIR /app
COPY target/Prueba1-0.0.1-SNAPSHOT.jar app.jar
RUN chown spring:spring app.jar
USER spring
EXPOSE 8080
ENV JAVA_OPTS="-Xmx512m -Xms256m"
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
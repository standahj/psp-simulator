# Java 17 base image
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app
COPY target/psp-simulator.jar /app/
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "psp-simulator.jar"]

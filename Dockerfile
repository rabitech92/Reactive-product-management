# Use OpenJDK as base image
FROM openjdk:21-jdk-slim
# Set working directory
WORKDIR /app
# Copy built JAR file
COPY target/supershop-management.jar app.jar
# Expose port
EXPOSE 8080
# Run the application
CMD ["java", "-jar", "app.jar"]

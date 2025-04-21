# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the .jar file from your local machine into the container
COPY target/MindMap-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the port that the Spring Boot application will run on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

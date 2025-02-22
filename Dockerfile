# Use OpenJDK base image
FROM openjdk:17

# Set the working directory
WORKDIR /app

# Copy the jar file into the container
COPY target/WeatherApp-Version-0.0.1.jar /app/WeatherApp-Version-0.0.1.jar

# Expose the port the application runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "WeatherApp-Version-0.0.1.jar"]

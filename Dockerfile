# Use the official OpenJDK image with Java 17 as the base image
FROM openjdk:17-jdk-alpine


# Copy the project directory
COPY . .

# Expose the port that Spring Boot app will run on
EXPOSE 8080

# Generate the JAR file
#
#  -DskipTests: skip running the tests
#  enable this flag on first run if postgres's container is not running yet
#
RUN ./mvnw clean package -DskipTests


# Define the working directory inside the container
WORKDIR /app

# Copy the JAR file to the container
RUN cp ../target/PressureMonitor-0.0.1-SNAPSHOT.jar .

# Command to run your application
CMD ["java", "-jar", "PressureMonitor-0.0.1-SNAPSHOT.jar"]
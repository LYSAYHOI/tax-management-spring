# Use a base image with a JRE (Java Runtime Environment) based on Java 17
FROM adoptopenjdk:17-jre-hotspot

# Set the working directory inside the container
WORKDIR /app

# Copy the executable JAR or WAR file into the container
COPY tax-management-spring.jar /app/tax-management-spring.jar

# Expose the port that the Spring Boot application will run on
EXPOSE 8080

# Specify the command to run your Spring Boot application
CMD ["java", "-jar", "tax-management-spring.jar"]
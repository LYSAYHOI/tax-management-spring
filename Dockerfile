# Use a base image with a JRE (Java Runtime Environment) based on Java 17
FROM openjdk:17.0.2-jdk-oracle

# Set the working directory inside the container
WORKDIR /app

# Copy the executable JAR or WAR file into the container
COPY target/tax-management-spring.jar tax-management-spring.jar

# Copy cacert
COPY cacert/cacerts cert/cacerts

# Expose the port that the Spring Boot application will run on
EXPOSE 8080

# Specify the command to run your Spring Boot application
CMD ["java","-Djavax.net.ssl.trustStore=cert/cacerts", "-Djavax.net.ssl.trustStore=cert/cacerts", "-Djavax.net.ssl.trustStorePassword=changeit", "-jar", "tax-management-spring.jar"]

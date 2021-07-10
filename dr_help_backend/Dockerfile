FROM maven:3.8.1-jdk-8 AS dr_help_backend

WORKDIR /usr/src/dr_help_backend
COPY . .
RUN ["mvn", "package", "-DskipTests"]
EXPOSE 8080
CMD ["java", "-jar", "target/dr_help.jar"]
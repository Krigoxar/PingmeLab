# 1. Building the App with Maven
FROM maven:3.8.3-openjdk-17

ADD . /PingmeLab
WORKDIR /PingmeLab

# Just echo so we can see, if everything is there :)
RUN ls -l

# Run Maven build
RUN mvn clean install


# Just using the build artifact and then removing the build-container
FROM openjdk:17.0.2-jdk

LABEL org.opencontainers.image.authors="Nikita Gorchakov"

# Add Spring Boot app.jar to Container
COPY --from=0 "/PingmeLab/backend/target/backend-0.0.1-SNAPSHOT.jar" app.jar

ENV JAVA_OPTS=""

# Fire up our Spring Boot app by default
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar" ]
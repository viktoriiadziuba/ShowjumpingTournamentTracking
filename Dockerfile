FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=/build/libs/gs-spring-boot-docker-0.1.0.jar
ADD ${JAR_FILE} gs-spring-boot-docker-0.1.0.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/gs-spring-boot-docker-0.1.0.jar"]

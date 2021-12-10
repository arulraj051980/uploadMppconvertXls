FROM openjdk:8-jdk-alpine
VOLUME /uploadfiles
ADD target/uploadfiles-0.0.1.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
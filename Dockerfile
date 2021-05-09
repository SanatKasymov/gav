FROM openjdk:latest
ADD target/gav-app.jar gav-app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "gav-app.jar"]

FROM amazoncorretto:11-alpine-jdk
COPY target/spring-security-webflux-jwt-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
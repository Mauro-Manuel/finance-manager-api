FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/maneymanager-0.0.1-SNAPSHOT.jar maneymanager-v1.0.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "maneymanager-0.0.1-SNAPSHOT.jar"]
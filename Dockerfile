FROM openjdk:11-jre-slim
COPY target/rinat-muratidinov-1.0.jar rinat-muratidinov-1.0.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "rinat-muratidinov-1.0.jar"]
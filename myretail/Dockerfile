FROM java:openjdk-8-alpine
ADD my-retail-0.0.1-SNAPSHOT.jar build/libs/my-retail-0.0.1-SNAPSHOT.jar
EXPOSE 9898
ENV MONGODB_DB_NAME retail
ENV MONGODB_DB_HOST mongo
ENV MONGODB_DB_PORT 27017
ENTRYPOINT ["java", "-jar", "build/libs/my-retail-0.0.1-SNAPSHOT.jar"]

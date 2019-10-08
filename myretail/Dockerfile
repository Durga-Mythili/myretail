FROM java:openjdk-8-alpine
ENV MONGO_URL mongodb://localhost/retail_service
EXPOSE 9898
RUN mkdir -p /app/
ADD build/libs/myretail-0.0.1-SNAPSHOT.jar /app/myretail.jar
ENTRYPOINT ["java", "-jar", "/app/myretail.jar"]
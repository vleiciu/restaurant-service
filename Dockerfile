FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
EXPOSE 8083
ARG JAR_FILE=target/RestaurantService-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} RestaurantService-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/RestaurantService-0.0.1-SNAPSHOT.jar"]
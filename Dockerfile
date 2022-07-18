FROM amazoncorretto:17
MAINTAINER darcyxian.com
COPY build/libs/weatherRestAPI-0.0.1-SNAPSHOT.jar  weather-server-1.0.0.jar
ENTRYPOINT ["java","-jar","/weather-server-1.0.0.jar"]
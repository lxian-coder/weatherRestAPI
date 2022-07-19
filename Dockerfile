FROM amazoncorretto:17 as builder
RUN mkdir -p /app/source
COPY . /app/source
WORKDIR /app/source
RUN ./gradlew clean build

FROM amazoncorretto:17 as Runtime
COPY --from=builder /app/source/build/libs/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]


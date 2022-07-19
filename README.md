
# WeatherRESTfulAPI Backend Sample application
## Tech Stack:
* SpringBoot
* Spring Security (API key)
* Docker
* Lombok
* Mapstruct
* Spring Data JPA
* H2 in-memory DB
* Mockito
* Junit
* MockMvc
* Flyway

## Topics
1. [How to run this application](#How-to-run-this-application)
2. [How to access the spring boot restful application](#How-to-access-the-spring-boot-restful-application)
3. [Advantages of this application](#Advantages-of-this-application)

### This is the springBoot based RESTful API. It supports the below functions:

* Application support H2 in-mem DB.
* Provide current weather information of the city user searched.
* API key security check
* URL: /weather/{city name}/{country name}



## How to run this application

* Navigate the root folder /weatherRestAPI under the command line
* Run the command to build the whole project: **gradle clean build**
* Either run the command to start the application: **java -jar ./build/libs/weatherRestAPI-0.0.1-SNAPSHOT.jar**
* Or run **docker build --tag=weather-server:latest .**, and **docker run -p8080:8080  weather-server:latest** then application is up and running in a docker container.

## How to access the spring boot restful application
### Application by default provide 5 API keys: demoKey1, demoKey2, demoKey3, demoKey4, demoKey5
#### HTTP.requests need attach one of those keys to the request header: X-API-key
#### Each key can only be used 5 times per hour.
#### HTTP.requests need provide city name and country name in URL, like : /weather/{city name}/{country name}   -->    /weather/New York/US  


### Please find the postman collection file in postman folder and import into your postman to check more details.


## Advantages of this application
* Hibernate builds the entity layer to connect database server
* Flyway prepared the initial data.
* Spring Data JPA builds the repository layer (DAO)
* Mapstruct used to map data between entities and dtos
* Mockito and MockMvc unit test service and controller layer
* All the exceptions can be centrally handled in one place (GlobalExceptionHandler.java)
* Lombok makes our life easierIt automatically generates getter,setter, constructor, hashcode, log etc.
* Utilize Spring security to implement API keys authentication.
* Dockerfile utilize multi-stage and put application files into docker instead of using a fat jar.


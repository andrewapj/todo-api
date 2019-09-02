# TODO-API - Work in progress

A Todo application with a REST API

## Features

* CRUD actions on "TodoList" and "Todo Item".
* H2 DB
* REST API
* Actuator endpoints.
* Kafka integration
* High test coverage
* Postman requests available
* Docker support

## Building

### Building a Jar

```mvn clean package```  

Alternatively you can use the maven wrapper script ```mvnw``` or ```mvnw.cmd```
  
This will build the application jar, run the tests and run the code quality plugins 
(Checkstyle, Spotbugs, JaCoCo )

### Building a docker image

- Run docker locally on your machine  
- ```mvn compile jib:dockerBuild``` 

## Running

### Running locally

```mvn spring-boot:run```

### Running via docker

- Run docker locally on your machine
- ```docker run -p8080:8080 todo-api```

## Notes

- The JaCoCo reports are located under ```target/site/jacoco```

# AutoComplete Java
Java + Spring + Hibernate + Mysql + JUnit

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 4](https://maven.apache.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.powerschool.assignment.AssignmentApplication` class from your IDE.

- One can run the JUnits while building the application [ maven - clean, install ] <br/>
- One can run the IT Test - [`[ src/test/java ] com.powerschool.assignment.rest.census.CensusControllerIT`] once the application is up and running

## Pre-rerequisites

Below MySql table structure to be created and the csv uploaded in the mysql-data folder to be dumped into this table

``` 
CREATE TABLE 'us_names' (
  'id' int(11) NOT NULL AUTO_INCREMENT,
  'firstname' varchar(200) NOT NULL,
  PRIMARY KEY ('id'),
  UNIQUE KEY 'firstname_UNIQUE' ('firstname')
) ENGINE=InnoDB AUTO_INCREMENT=151719 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```
Once the mysql is installed and ready to go, please change the application.properties accordingly

```
spring.datasource.url=jdbc:mysql://localhost:3306/data
spring.datasource.username=
spring.datasource.password=
spring.datasource.driver.class=com.mysql.jdbc.Driver
```

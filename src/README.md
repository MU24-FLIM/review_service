# Microservices-applikation med Spring Boot och MySQL
## A movie review application

This is a school project made with IntelliJ Java, MySQL, Spring boot, REST API, CRUD and JPA.
A group project to build an application consisting of four separate micro services.
The project group:

- - Linda: Reviews
- - Fredrik: Movies
- - Ivana: Users
- - Madeleine: Genres

To run this project you need to:
Clone all repositories.
Read the README of each project to find information about the services.
Set up the databases in MySQL.
Set up your environmental variables in each service (in the resource application.properties, you´ll find names used for each parameter.).
Remember to include the different webclients for the different services.
Run each Java-application. They will create the necessary tables and mock data to start with.
Use Postman to test the API.

Check it out for yourself! Hope you like it.

## Service specific Review:
SQL-Script for MySQL:

CREATE DATABASE review_service_db;
USE review_service_db;

###  Setting up environmental variables:

Review-service: 
DB_URL - URL to the database. For example jdbc:mysql://localhost:3306/review_service_db
DB_USER - Your server username
MYSQL_PASSWORD - Your server password 
MOVIE_CLIENT_URL - http://localhost:8080 
USER_CLIENT_URL - http://localhost:8082

Postman:
Base-URL : http://127.0.0.1:8081

| CRUD   | Operation               | Endpoint                |
|--------|-------------------------|-------------------------|
| POST   | Create a new review     | `/reviews`              |
| GET    | Get all reviews         | `/reviews`              |
| GET    | Get review by id        | `/reviews/{id}`         |
| GET    | Get reviews of movie ID | `/reviews/{id}/reviews` |
| PUT    | Update review by ID     | `/reviews/{id}`         |
| DELETE | Delete a review by ID   | `/reviews/{id}`         |

Syntax for creating/updating a review

````json
{
  "movieId": 27,
  "userId": 2,
  "title": "Fantastic",
  "content": "What a masterpiece! Great actors, good story.",
  "rating": 5
}
````
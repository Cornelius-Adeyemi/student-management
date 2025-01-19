# Student management

This is a Spring Boot application that .


**Note - to run this application you can either run the docker compose file using "docker-compose up" command or run locally **

There are so many thing that are still needed to be done on the project that I couldn't do due to time constraint. 
This is a monolithic backend.

The admin user is created programmatically. This is created on running the application. The password and email can be changed
in the application.properties file and it is 

The endpoint can be tested using swagger 

## Features
### Admin
-Admin need to login
http://localhost:8888/api/v1/auth/login

- admin can add student
  http://localhost:8888/api/v1/admin/add-student
  {
  "firstName" :"Adebis",
  "lastName" : "lastname",
  "email" : "aadebisi135@gmail.com",
  "password": "1234567"

}

- admin can grade a student 

http://localhost:8888/api/v1/admin/grade-student

{
"studentId": "S-7476",
"subjectGrades": [

        {
            "subject" : "MATH",
             "score"  : 70
        },
          {
            "subject" : "ENGLISH",
             "score" : 70
        },
          {
            "subject" :  "PHYSICS",
             "score" :40
        },
          {
            "subject" :"CHEMISTRY" ,
             "score" : 20
        },
          {
            "subject" :"BIOLOGY" ,
             "score" : 85
        }
    ]
}

- Get all student
  http://localhost:8888/api/v1/admin/get-all-student

- generate report for all student
  http://localhost:8888/api/v1/admin/generate-report

# Algorithm and Database

for the algorithm question, check service directory for the solutions. The test cases are in the test directory.

## for database question

check the resources file for tha database question 


## Technologies Used

- Java 17
- Spring Boot 3.2.5
- Spring Data JPA
- Spring Security
- Mockito
- JUnit 5
- POSTGRESQL
- 
- ## Getting Started

1. Clone the repository:

   ```bash
   

2. Navigate to the project directory:
bash
cd bookstore

3. Build the project:
bash
./mvnw clean install

4. Run the application:
bash
./mvnw spring-boot:run

5. Access the application at http://localhost:8888.

- ## Testing

-To run the unit tests:
bash
./mvnw test

-The tests cover the service layers and repository layers using Mockito.

## Api Docummentaion
The api docummetation was done using SpringFox library for swagger configuration, which reveals every endpoint and their respective request as well as response.

- ## Security
The application uses Spring Security for authentication and authorization. The default username is admin and the default password is password.

- ## Logging
Logging is implemented using Spring Slfj4. The logs are written to the console and can be configured in the application.properties file.

- ## Configuration
The application can be configured using the application.properties file. You can set the database connection details, caching configurations, and other settings.

- ## Contributing
If you find any issues or have suggestions for improvements, feel free to create a new issue or submit a pull request.

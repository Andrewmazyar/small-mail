# small-mail

# Context

- [1. Project Purpose](#1-project-purpose)
- [2. Project Structure](#2-project-structure)
- [3. For Developers](#3-for-developers)
- [4. Author](#4-author)

## 1.Project Purpose 
this project is a simple version email where user has two roles "USER" and "APICALL" where:
* "USER" can:
    * display all active users
    * enter to own page and chose another role
*  "APICALL" can:
    * write message
    * detail message
    * reply to message
    * looking message by phrase
    * group delete letters with checkbox

this project has authentication and authorization filters, Repository, Service and Controller layers.
Repository layer has implementation on H2 DB.

## 2. Project Structure

* Java 11
* Spring Boot 
* H2DB

## 3. For Developer

To run this project you need install:
 
 * Java 11
 * maven
 
for run this app you should do in console:
 
 * mvn clean package
 * java - jar target/small-mail-0.0.1-SNAPSHOT.jar
 
When you start project you will receive 5 users with email and password:
and you receive 5 users
* name: user@gmail.com password: password
* name: president@gmail.com password: president
* name: minister@gmail.com password: minister
* name: deputy@gmail.com password: deputy
* name: citizen@gmail.com password: citizen

where you can communicate between them with the follow urls:
* /users-details/** you pass to own page user and can include use Api for your app
    * for use role APICALL you should logout and again login
* / - you see all message and you can search messages by phrase
* /mail - you can write any message to someone
* /detail/1 - you can see all details about letter from or to whom this letter
* /users - you can see all valid users in this app

## 4. Author
<li><a href="https://github.com/Andrewmazyar">Andriy Maziar</a></li>

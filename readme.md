README

[![Build Status](https://travis-ci.org/pwdd/java-server.svg?branch=master)](https://travis-ci.org/pwdd/java-server)

## HTTP Server

- Project uses:
  - Java 1.8.0
  - JUnit 4.12
  - Maven 3.3.9

- Creating jar file: `mvn package`

- Starting the server: 
    - `java -jar target/server-0.1.jar` (defaults to port 8080 and directory /foo)
    - `java -jar target/server-0.1.jar -p <port-number> -d <directory-name>` 
    
- Valid ports: from 1 to 65535

- Quitting the server: `CTRL+C`

- Running the tests: `mvn test`

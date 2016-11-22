README

## HTTP Server

- Project uses:
  - Java 1.8.0
  - JUnit 4.12
  - Maven 3.3.9

- Creating jar file: `mvn package`

- Starting the server: `java -jar target/httpServer-0.1.jar -p <port-number> -d <directory-name>` 
    
    (Default: port: 8080, directory: "foo")

- Quitting the server: `CTRL+C`

- Running the tests: `mvn test`

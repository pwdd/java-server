README

## HTTP Server

- Project uses:
  - Java 1.8.0
  - JUnit 4.12
  - Maven 3.3.9

- Creating jar file: `mvn package`

- Starting the server: `java -jar target/httpServer-0.1.jar -p <port-number> -d <directory-name>` 
    
- Defaults:
 
    - Port: 8080 
    - Directory: "./foo"

- Possible options: 
    - Valid ports: from 1 to 65535
    - Easy to access directories: "./foo" and "./bar"

- Quitting the server: `CTRL+C`

- Running the tests: `mvn test`

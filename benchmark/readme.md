README

Benchmarking uses [Apache Bench](https://httpd.apache.org/docs/2.4/programs/ab.html) + [gnGNUPlot](http://www.gnuplot.info/) to generate the graphs showing how long a response takes over time. The image of heap memory usage (`HeapMemoryUsage.png`) is a screen shot of a profiling session using [VisualVM](https://visualvm.github.io/).

- **benchmark/before**: Before optimization, files were read at once into a `byte[]` and them send through Socket
  
  Reference: 
  
  - Reading a file: [`FileReader.fileToByteArray`](https://github.com/pwdd/java-server/blob/489d90a57c0979d752ea5be33bd78ae3624276ff/src/main/java/com/pwdd/server/responders/GET/FileReader.java#L26)
             
  - Sending a file: [`ConnectionManager.sendResponseTo`](https://github.com/pwdd/java-server/blob/489d90a57c0979d752ea5be33bd78ae3624276ff/src/main/java/com/pwdd/server/server/ConnectionManager.java#L26)
  
- **benchmark/after**: Optimization uses `FileInputStream` to read file and buffer to send file through socket

  Reference: 
  
  - Reading a file: [`FileReader.body`](https://github.com/pwdd/java-server/blob/master/src/main/java/com/pwdd/server/responders/GET/FileReader.java#L7)
  
  - Sending a file: [`ConnectionManager.sendResponseTo`](https://github.com/pwdd/java-server/blob/master/src/main/java/com/pwdd/server/connection/ConnectionManager.java#L28)
   
- **benchmark/concurrency**: profile the server after adding capacity to handle four concurrent requests

  Reference: [`Server.pool`](https://github.com/pwdd/java-server/blob/master/src/main/java/com/pwdd/server/connection/Server.java#L14)
  
  
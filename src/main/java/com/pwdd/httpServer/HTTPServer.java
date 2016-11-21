package com.pwdd.httpServer;

public class HTTPServer {
  public static void main( String[] args ) {
    DirValidation.exitInvalidDir(args);
    String dir = DirValidation.getDirectory(args);
    FileHandler fileHandler = new FileHandler(dir);
    fileHandler.createIndex();
    new Server(dir).run();
  }
}

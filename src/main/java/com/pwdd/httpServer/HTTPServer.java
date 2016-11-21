package com.pwdd.httpServer;

public class HTTPServer {
  public static void main( String[] args ) {
    DirValidation.exitInvalidDir(args);
    String dir = DirValidation.getDirectory(args);
    new Server(dir).run();
  }
}

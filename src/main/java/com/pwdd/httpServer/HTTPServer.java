package com.pwdd.httpServer;

public class HTTPServer {
  public static void main( String[] args ) {
    ArgumentsValidation.exitInvalidDir(args);
    String dir = ArgumentsValidation.getDirectory(args);
    new Server(dir).run();
  }
}

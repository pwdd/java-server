package com.pwdd.httpServer;

public class HTTPServer {
  public static void main( String[] args ) {
    Router.exitInvalidDir(args);
    String dir = Router.getDirectory(args);
    new Server().run();
  }
}

package com.pwdd.httpServer;

public class HTTPServer {
  public static void main( String[] args ) {
    ArgumentsValidation.exitOnInvalidArgs(args);
    String dirName = ArgumentsValidation.getDirectory(args);
    int porNumber = Integer.parseInt(ArgumentsValidation.getPortNumber(args));
    new Server(dirName, porNumber).run();
  }
}

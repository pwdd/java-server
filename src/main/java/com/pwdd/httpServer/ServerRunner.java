package com.pwdd.httpServer;

final class ServerRunner {
  private ServerRunner() {}

  private static String getDirName(String[] args) {
    return ArgumentsValidation.getDirectory(args);
  }

  private static int getPortNumber(String[] args) {
    return Integer.parseInt(ArgumentsValidation.getPortNumber(args));
  }

  static void start(String[] args) {
    ArgumentsValidation.exitOnInvalidArgs(args);
    String dirName = getDirName(args);
    int portNumber = getPortNumber(args);
    new Server(dirName, portNumber).run();
  }
}
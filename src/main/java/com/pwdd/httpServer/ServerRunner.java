package com.pwdd.httpServer;

final class ServerRunner {
  private ServerRunner() {}

  private static int getPortNumber(String[] args) {
    return Integer.parseInt(ArgumentsValidation.getPortNumber(args));
  }

  private static String getRootDirectory(String[] args) {
    return ArgumentsValidation.getDirectory(args);
  }

  private static IHandler[] createHandlers(String rootDirectory) {
    return new IHandler[] { new HelloWorldHandler(), new FileHandler(rootDirectory) };
  }

  private static Responder createResponder(IHandler[] handlers) {
    return new Responder(handlers);
  }

  static void start(String[] args) {
    ArgumentsValidation.exitOnInvalidArgs(args);
    String dirName = getRootDirectory(args);
    int portNumber = getPortNumber(args);
    Responder responder = createResponder(createHandlers(dirName));
    new Server(portNumber, responder).run();
  }
}
package com.pwdd.httpServer;

final class ServerRunner {
  private ServerRunner() {}

  private static int getPortNumber(String[] args) {
    return Integer.parseInt(ArgumentsValidation.getPortNumber(args));
  }

  private static String getRootDirectory(String[] args) {
    return ArgumentsValidation.getDirectory(args);
  }

  private static IResponder[] createHandlers(String rootDirectory) {
    return new IResponder[] { new HelloWorldResponder(), new IndexResponder(rootDirectory) };
  }

  private static Response createResponder(IResponder[] handlers) {
    return new Response(handlers);
  }

  static void start(String[] args) {
    ArgumentsValidation.exitOnInvalidArgs(args);
    String dirName = getRootDirectory(args);
    int portNumber = getPortNumber(args);
    Response response = createResponder(createHandlers(dirName));
    new Server(portNumber, response).run();
  }
}
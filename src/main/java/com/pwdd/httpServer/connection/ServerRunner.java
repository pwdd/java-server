package com.pwdd.httpServer.connection;

import com.pwdd.httpServer.Response;
import com.pwdd.httpServer.responders.*;
import com.pwdd.httpServer.utils.ArgumentsValidation;

public final class ServerRunner {
  private ServerRunner() {}

  private static int getPortNumber(String[] args) {
    return Integer.parseInt(ArgumentsValidation.getPortNumber(args));
  }

  private static String getRootDirectory(String[] args) {
    return ArgumentsValidation.getDirectory(args);
  }

  private static IResponder[] createHandlers(String rootDirectory) {
    return new IResponder[] { new HelloWorldResponder(), new IndexResponder(rootDirectory), new DownloadableResponder() };
  }

  private static Response createResponder(IResponder[] handlers) {
    return new Response(handlers);
  }

  public static void start(String[] args) {
    ArgumentsValidation.exitOnInvalidArgs(args);
    String dirName = getRootDirectory(args);
    int portNumber = getPortNumber(args);
    Response response = createResponder(createHandlers(dirName));
    new Server(portNumber, response).run();
  }
}
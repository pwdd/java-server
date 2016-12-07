package com.pwdd.server.server;

import com.pwdd.server.utils.ArgumentsValidation;

import java.io.File;

public final class ServerRunner {
  private ServerRunner() {}

  private static int getPortNumber(String[] args) {
    return Integer.parseInt(ArgumentsValidation.getPortNumber(args));
  }

  private static File getRootDirectory(String[] args) {
    String directory = ArgumentsValidation.getDirectory(args);
    return new File(System.getProperty("user.dir"), directory);
  }

  public static void start(String[] args) {
    ArgumentsValidation.exitOnInvalidArgs(args);
    File root = getRootDirectory(args);
    int portNumber = getPortNumber(args);
    new Server(portNumber, root).run();
  }
}
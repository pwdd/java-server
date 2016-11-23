package com.pwdd.httpServer;

import java.util.Arrays;
import java.util.List;

final class ArgumentsValidation {
  static String defaultDir = "./foo";
  static String defaultPortNumber = "8080";

  private ArgumentsValidation() {}

  static void exitOnInvalidArgs(String[] args) {
    if (!isValidArgs(args) || !isValidPortNumber(getPortNumber(args))) {
      System.out.println("invalid arguments");
      System.exit(0);
    }
  }

  static Boolean isValidArgs(String[] args) {
    return args.length == 0 || ((startsWithFlag(args) && !sameArg(args)) && hasPortOrDir(args));
  }

  private static Boolean startsWithFlag(String[] args) {
    if (args.length == 2) {
      return args[0].equals("-p") || args[0].equals("-d");
    }
    else {
      return (args.length == 4) &&
          (args[0].equals("-p") || args[0].equals("-d")) &&
          (args[2].equals("-p") || args[2].equals("-d"));
    }
  }

  private static Boolean sameArg(String[] args) {
    return args.length == 4 && args[0].equals(args[2]);
  }

  private static Boolean hasPortOrDir(String[] args) {
    return (args.length == 2 || args.length == 4) &&
        getIndexOf(args, "-d") != -1 &&
        getIndexOf(args, "-p") != -1;
  }

  static String getDirectory(String[] args) {
    return getArg(args, "-d", defaultDir);
  }

  static String getPortNumber(String[] args) {
    return getArg(args, "-p", defaultPortNumber);
  }

  private static String getArg(String[] args, String arg, String defaultResult) {
    List<String> argsList = Arrays.asList(args);
    if (argsList.contains(arg)) {
      return args[getIndexOf(args, arg)];
    }
    else {
      return defaultResult;
    }
  }

  private static Boolean isValidPortNumber(String port) {
    if (isNumeric(port)) {
      int portNumber = Integer.parseInt(port);
      return portNumber > 0 && portNumber <= 65535;
    } else {
      return false;
    }
  }

  private static Boolean isNumeric(String str) {
    return str.matches("\\d+");
  }

  private static int getIndexOf(String[] args, String arg) {
    List<String> argsList = Arrays.asList(args);
    return argsList.indexOf(arg) + 1;
  }
}

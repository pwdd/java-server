package com.pwdd.httpServer;

final class Router {
  private Router() {}

  static String forRequested(String uri) {
    if (uri.equalsIgnoreCase("/hello")) {
      return "Hello, world";
    } else {
      return "";
    }
  }

  private static Boolean isValidDirectory(String dirName) {
    return (dirName.equals("foo") || dirName.equals("bar"));
  }

  static String getDirectory(String[] args) {
    if (args.length == 0) {
      return "foo";
    }
    else if (isValidDirectory(args[0])) {
      return args[0];
    } else {
      return "foo";
    }
  }

  static void exitInvalidDir(String[] args) {
    if (args.length > 0 && !isValidDirectory(args[0])) {
      System.out.println("not a valid directory name");
      System.exit(0);
    }
  }
}

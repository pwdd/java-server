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
}

package com.pwdd.httpServer;

class Router {
  private FileHandler fileHandler;

  Router(String dir) {
    this.fileHandler = new FileHandler(dir);
  }

  String forRequested(String uri) {
    if (uri.equals("/")) {
      return fileHandler.index();
    }
    else if (uri.equalsIgnoreCase("/hello")) {
      return "Hello, world";
    } else {
      return "";
    }
  }

  String contentType(String uri) {
    if (uri.equals("/")) {
      return "text/html";
    } else {
      return "text/plain";
    }
  }
}

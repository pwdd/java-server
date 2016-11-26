package com.pwdd.httpServer;

class HelloWorldHandler implements IHandler {
  public String respond(String uri) {
    return "Hello, world";
  }

  public boolean canRespond(String uri) {
    return uri.equalsIgnoreCase("/hello");
  }
}

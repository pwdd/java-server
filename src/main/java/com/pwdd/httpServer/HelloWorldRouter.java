package com.pwdd.httpServer;

class HelloWorldRouter implements IRouter {
  public String respond() {
    return "Hello, world";
  }

  public boolean canRespond(String uri) {
    return uri.equalsIgnoreCase("/hello");
  }

  public String setContentType() {
    return "text/plain";
  }
}

package com.pwdd.httpServer;

class HelloWorldRouter implements IRouter {

  public boolean canRespond(String uri) {
    return uri.equalsIgnoreCase("/hello");
  }

  public String header(String date) {
    String contentType = "text/plain";
    String crlf = "\r\n";
    return "HTTP/1.1 200 OK" + crlf +
        "Date: " + date + crlf +
        "Content-Type: " + contentType + crlf +
        crlf;
  }

  public String body() {
    return "Hello, world";
  }
}
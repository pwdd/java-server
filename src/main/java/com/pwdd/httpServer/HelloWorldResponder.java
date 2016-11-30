package com.pwdd.httpServer;

class HelloWorldResponder implements IResponder {

  public boolean canRespond(String uri) {
    return uri.matches("(?i:.*^/hello/?$.*)");
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
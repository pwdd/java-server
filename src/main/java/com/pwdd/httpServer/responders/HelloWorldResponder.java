package com.pwdd.httpServer.responders;

public class HelloWorldResponder implements IResponder {

  public boolean canRespond(String uri) {
    return uri.matches("(?i:.*^/hello/?$.*)");
  }

  public byte[] header(String date) {
    String contentType = "text/plain";
    String crlf = "\r\n";
    return ("HTTP/1.1 200 OK" + crlf +
        "Date: " + date + crlf +
        "Content-Type: " + contentType + crlf +
        crlf).getBytes();
  }

  public byte[] body(String uri) {
    return "Hello, world".getBytes();
  }
}
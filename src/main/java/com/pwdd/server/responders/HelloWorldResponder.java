package com.pwdd.server.responders;

public class HelloWorldResponder implements IResponder {

  public boolean canRespond(String uri) {
    return uri.matches("(?i:.*^/hello/?$.*)");
  }

  public byte[] header(String date) {
    String responseHeader = "HTTP/1.1 200 OK" +
        CRLF +
        "Content-Type: text/plain" +
        CRLF +
        "Date: " +
        date +
        CRLF +
        CRLF;
    return responseHeader.getBytes();
  }

  public byte[] body(String uri) {
    return "Hello, world".getBytes();
  }
}

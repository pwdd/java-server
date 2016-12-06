package com.pwdd.server.responders;

import java.io.File;

public class HelloWorldResponder implements IResponder {

  public boolean canRespond(File file) {
    return file.getPath().toLowerCase().matches(".*^/hello/?$.*");
  }

  public byte[] header(File file, String date) {
    String responseHeader = "HTTP/1.1 200 OK" + CRLF +
        "Content-Type: text/plain" + CRLF +
        "Date: " + date + CRLF +
        CRLF;
    return responseHeader.getBytes();
  }

  public byte[] body(File file) {
    return "Hello, world".getBytes();
  }
}

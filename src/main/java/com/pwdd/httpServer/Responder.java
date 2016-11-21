package com.pwdd.httpServer;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

class Responder {
  private FileHandler fileHandler;

  Responder(String dir) {
    this.fileHandler = new FileHandler(dir);
  }

  String defaultHeader(String contentType) {
    String crlf = "\r\n";
    return "HTTP/1.1 200 OK" + crlf +
        "Date: " + getDateInUTC0() + crlf +
        "Content-Type: " + contentType + crlf +
        crlf;
  }

  String bodyForRequested(String uri) {
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

  private String getDateInUTC0() {
    ZonedDateTime date = ZonedDateTime.now(ZoneOffset.UTC);
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z");
    return dateFormat.format(date);
  }
}
